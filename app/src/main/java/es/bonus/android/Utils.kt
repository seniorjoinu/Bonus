package es.bonus.android

import androidx.ui.text.font.ResourceFont
import androidx.ui.text.font.fontFamily
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeRange
import com.soywiz.klock.DateTimeSpan
import com.soywiz.klock.MonthSpan
import java.time.temporal.TemporalField
import java.util.*

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
        dif.years > 0 -> "${dif.years} years ago"
        dif.months > 0 -> "${dif.months} months ago"
        dif.weeks > 0 -> "${dif.weeks} weeks ago"
        dif.days > 0 -> "${dif.days} days ago"
        dif.hours > 0 -> "${dif.hours} hours ago"
        dif.minutes > 0 -> "${dif.minutes} minutes ago"
        else -> "just now"
    }
}