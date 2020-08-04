package es.bonus.android.features

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import com.soywiz.klock.DateTime
import com.soywiz.klock.years
import es.bonus.android.data.OwnedAsset
import es.bonus.android.data.UserEvent
import es.bonus.android.data.UserEventType
import es.bonus.android.data.ValueAsset
import es.bonus.android.state
import java.util.*
import kotlin.random.Random

data class EventState(
    val events: List<UserEvent> = emptyList(),
    val fetching: Boolean = false,
    val error: Throwable? = null
)

typealias EventStore = MutableState<EventState>

@Composable
fun createEventStore() = state { EventState() }

fun EventStore.fetch() {
    value = state.copy(fetching = true)

    try {
        val events = getRandomEvents(4)
        value = state.copy(events = events, error = null)
    } catch (e: Throwable) {
        value = state.copy(error = e)
    } finally {
        value = state.copy(fetching = false)
    }
}


// ------------------------------------------------------------------------------
enum class Companies(val value: Company) {
    McDoodles(Company(name = "McDoodles")),
    VapeShop(Company(name = "VapeShop")),
    BeutifulCompany(Company(name = "Beautiful Company"));

    companion object {
        fun random(): Company = values().random().value
    }
}

fun randomCurrency() = Random.nextInt(0, 20000).toBigInteger()
fun randomPercent() = Random.nextInt(1, 100).toByte()
fun randomTimestamp() = Random.nextLong(
    (DateTime.now() - 1.years).unixMillisLong,
    Date().time
)

fun getRandomEvents(count: Int): List<UserEvent> {
    return (0 until count)
        .map {
            UserEvent(
                type = UserEventType.random(),
                ownedAsset = if (Random.nextBoolean()) {
                    OwnedAsset.Bonus(
                        ValueAsset.Bonus(randomCurrency()),
                        Companies.random()
                    )
                } else {
                    if (Random.nextBoolean()) {
                        OwnedAsset.Discount.Currency(
                            ValueAsset.Currency.Ruble(randomCurrency()),
                            Companies.random()
                        )
                    } else {
                        OwnedAsset.Discount.Percent(
                            randomPercent(),
                            Companies.random()
                        )
                    }
                },
                timestamp = randomTimestamp().toBigInteger()
            )
        }
        .sortedBy { it.timestamp }
}