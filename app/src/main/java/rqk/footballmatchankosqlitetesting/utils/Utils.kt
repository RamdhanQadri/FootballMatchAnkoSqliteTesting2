package rqk.footballmatchankosqlitetesting.utils

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextView.normal() {
    typeface = Typeface.DEFAULT
}

fun TextView.bold() {
    typeface = Typeface.DEFAULT_BOLD
}

fun String.formatDate(fromDateFormat: String = "dd/MM/yy", toDateFormat: String = "E, dd MMM yyyy"): String? {
    val date = SimpleDateFormat(fromDateFormat, Locale.getDefault()).parse(this)
    val dateFormatter = SimpleDateFormat(toDateFormat, Locale.getDefault())
    return dateFormatter.format(date)
}