package markxie.game.qrcode.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Pattern: YYYY/MM/dd hh:mm
 *
 */
fun Date.getTodayAndMonth(): String {
    val sdf = SimpleDateFormat("YYYY/MM/dd hh:mm", Locale.getDefault())
    return sdf.format(this)
}

/**
 * H 24小時制, h 12小時制
 * Pattern: YYYY/MM/dd HH:mm
 *
 */
fun Long.convertLongToTime(): String {
    val format = SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.getDefault())
    return format.format(Date(this))
}

fun Long.convertToHour(): String {
    val sdf = SimpleDateFormat("HH", Locale.getDefault())
    return sdf.format(Date(this))
}