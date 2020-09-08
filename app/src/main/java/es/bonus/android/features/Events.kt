package es.bonus.android.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.soywiz.klock.DateTime
import com.soywiz.klock.years
import es.bonus.android.data.CompanyId
import es.bonus.android.data.OwnedAsset
import es.bonus.android.data.UserId
import es.bonus.android.data.ValueAsset
import es.bonus.android.state
import java.math.BigInteger
import java.util.*
import kotlin.random.Random

enum class EventType {
    COMPANY_BONUSES_ISSUED,
    COMPANY_BONUSES_WITHDRAWN,
    CUSTOMER_REWARD_PURCHASED,
    CUSTOMER_REWARD_USED;

    companion object {
        fun random() = values().random()
    }
}


// REFACTOR EVENTS AND ADD TO DUMMY SETUP


data class Event(
    val type: EventType,
    val companyId: CompanyId,
    val userId: UserId,
    val ownedAsset: OwnedAsset,
    val timestamp: BigInteger
)

data class EventState(
    val events: List<Event> = emptyList(),
    val fetching: Boolean = false,
    val error: Throwable? = null,
    val fetched: Boolean = false
)

typealias EventStore = MutableState<EventState>

@Composable
fun createEventStore() = remember {
    mutableStateOf(
        EventState()
    )
}

fun EventStore.fetchEvents(count: Int = 4) {
    value = state.copy(fetching = true)

    value = try {
        val events = randomEvents // TODO
        state.copy(events = events, error = null, fetching = false, fetched = true)
    } catch (e: Throwable) {
        state.copy(error = e, fetching = false)
    }
}

enum class EventEntity {
    USER, COMPANY
}

// ------------------------------------------------------------------------------

fun randomCurrency() = Random.nextInt(0, 20000).toBigInteger()
fun randomPercent() = Random.nextInt(1, 100).toByte()
fun randomTimestamp() = Random.nextLong((DateTime.now() - 1.years).unixMillisLong, Date().time)

private var randomEvents = getRandomEvents(4)
fun getRandomEvents(count: Int): List<Event> {
    return (0 until count)
        .map {
            val type = EventType.random()
            Event(
                type = type,
                ownedAsset = if (type == EventType.COMPANY_BONUSES_ISSUED || type == EventType.COMPANY_BONUSES_WITHDRAWN) {
                    OwnedAsset.Bonus(
                        ValueAsset.Bonus(randomCurrency())
                    )
                } else {
                    if (Random.nextBoolean()) {
                        OwnedAsset.Discount.Currency(
                            ValueAsset.Currency.Ruble(randomCurrency())
                        )
                    } else {
                        OwnedAsset.Discount.Percent(
                            randomPercent()
                        )
                    }
                },
                company = Companies.random(),
                user = Users.random(),
                timestamp = randomTimestamp().toBigInteger()
            )
        }
        .sortedBy { it.timestamp }
}