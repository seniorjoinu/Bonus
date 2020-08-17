package es.bonus.android

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.ambientOf
import androidx.ui.graphics.asImageAsset
import androidx.ui.text.font.ResourceFont
import androidx.ui.text.font.fontFamily
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeRange
import es.bonus.android.features.CompanyStore
import es.bonus.android.features.EventStore
import es.bonus.android.features.RoutingStore
import es.bonus.android.features.UserStore

object Fonts {
    val Inter = fontFamily(
        ResourceFont(resId = R.font.inter_all_in_one)
    )
    val MondaBold = fontFamily(
        ResourceFont(resId = R.font.monda_bold)
    )
}

const val GLOBAL_HOR_PADDING = 30

fun prettyTimestamp(value: Long): String {
    val date = DateTime.fromUnix(value)
    val now = DateTime.now()

    val dif = DateTimeRange(date, now).span

    return when {
        dif.years == 1 -> "${dif.years} year ago"
        dif.years > 0 -> "${dif.years} years ago"

        dif.months == 1 -> "${dif.months} month ago"
        dif.months > 0 -> "${dif.months} months ago"

        dif.weeks == 1 -> "${dif.weeks} week ago"
        dif.weeks > 0 -> "${dif.weeks} weeks ago"

        dif.days == 1 -> "${dif.days} day ago"
        dif.days > 0 -> "${dif.days} days ago"

        dif.hours == 1 -> "${dif.hours} hour ago"
        dif.hours > 0 -> "${dif.hours} hours ago"

        dif.minutes == 1 -> "${dif.minutes} minute ago"
        dif.minutes > 0 -> "${dif.minutes} minutes ago"

        else -> "just now"
    }
}

fun Context.getResourceBytes(resId: Int): ByteArray {
    val res = resources.openRawResource(resId)
    val resBytes = ByteArray(res.available())
    res.read(resBytes)

    return resBytes
}

object Ambients {
    val RoutingStore = ambientOf<RoutingStore> { error("No routing store found") }
    val UserStore = ambientOf<UserStore> { error("No user store found") }
    val EventStore = ambientOf<EventStore> { error("No event store found") }
    val CompanyStore = ambientOf<CompanyStore> { error("No company store found") }
}

@Composable
val <T : Any> Ambient<MutableState<T>>.state: T
    get() = current.state

val <T : Any> MutableState<T>.state: T
    get() = value

fun ByteArray.asImageAsset() = BitmapFactory.decodeByteArray(this, 0, size).asImageAsset()