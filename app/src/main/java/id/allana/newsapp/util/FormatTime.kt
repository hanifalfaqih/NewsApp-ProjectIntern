package id.allana.newsapp.util

import java.text.SimpleDateFormat
import java.util.Locale

fun textFormatTime(dateTime: String?): String? {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
    return dateTime?.let { parser.parse(it)?.let { date ->
        formatter.format(date) } }
}