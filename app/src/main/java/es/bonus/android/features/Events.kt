package es.bonus.android.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import es.bonus.android.data.*
import es.bonus.android.state
import java.math.BigInteger

typealias EventId = Id

sealed class Event {
    data class BonusesIssued(
        val id: EventId,
        val fromCompanyId: CompanyId,
        val toUserId: UserId,
        val amount: BigInteger,
        val timestamp: BigInteger
    ) : Event()

    data class BonusesTransferred(
        val id: EventId,
        val fromUserId: UserId,
        val toUserId: UserId,
        val ofCompanyId: CompanyId,
        val amount: BigInteger,
        val timestamp: BigInteger
    ) : Event()

    data class RewardPurchased(
        val id: EventId,
        val rewardId: RewardId,
        val price: BigInteger,
        val timestamp: BigInteger
    ) : Event()

    data class RewardAccepted(
        val id: EventId,
        val rewardId: RewardId,
        val timestamp: BigInteger
    ) : Event()

    data class ExchangeOfferAccepted(
        val id: EventId,
        val offerId: ExchangeOfferId,
        val byUserId: UserId,
        val timestamp: BigInteger
    ) : Event()
}

data class EventState(
    val events: List<Event> = emptyList(),
    val fetching: Boolean = false,
    val error: Throwable? = null,
    val fetched: Boolean = false
)

typealias EventStore = MutableState<EventState>

@Composable
fun createEventStore() = remember {
    mutableStateOf(EventState())
}

suspend fun EventStore.fetchEvents(
    count: Int = 4,
    getEvents: suspend () -> Result<List<Event>, String>
) {
    value = state.copy(fetching = true)

    value = try {
        val events = getEvents().unwrap().takeLast(count)
        state.copy(events = events, error = null, fetching = false, fetched = true)
    } catch (e: Throwable) {
        state.copy(error = e, fetching = false)
    }
}

enum class EventEntity {
    USER, COMPANY
}
