package es.bonus.android.features

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import com.soywiz.klock.DateTime
import com.soywiz.klock.years
import es.bonus.android.data.OwnedAsset
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

data class Event(
    val type: EventType,
    val company: Company,
    val user: User,
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
fun createEventStore() = state { EventState() }

fun EventStore.fetchEvents(count: Int = 4) {
    if (state.fetched) return // TODO: introduce fix

    value = state.copy(fetching = true)

    value = try {
        val events = getRandomEvents(count)
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