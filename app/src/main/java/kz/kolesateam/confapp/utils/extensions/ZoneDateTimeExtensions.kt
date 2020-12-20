package kz.kolesateam.confapp.utils.extensions

import org.threeten.bp.ZonedDateTime

fun ZonedDateTime.getEventFormattedDateTime(): String {
    val hourString: String = getNumberZeroPrefix(this.hour)
    val minuteString: String = getNumberZeroPrefix(this.minute)

    return "$hourString:$minuteString"
}

private fun getNumberZeroPrefix(
    number: Int
): String =
    if (number > 9) {
        number.toString()
    } else {
        "0$number"
    }