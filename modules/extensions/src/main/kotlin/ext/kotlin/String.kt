package ext.kotlin

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): String {
    return try {
        val d = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this)
        SimpleDateFormat("MMM d, yyyy", Locale.US).format(d)
    } catch (ignored: Exception) {
        ""
    }
}

fun String.toDateObject(): Date? {
    return try {
        SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this)
    } catch (ignored: Exception) {
        null
    }
}

fun String.toAge(): String {
    return try {
        val d = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this)
        val now = Calendar.getInstance()
        val dob = Calendar.getInstance().apply {
            time = d
        }
        val year1 = now.get(Calendar.YEAR)
        val year2 = dob.get(Calendar.YEAR)
        var age = year1 - year2
        val month1 = now.get(Calendar.MONTH)
        val month2 = dob.get(Calendar.MONTH)
        if (month2 > month1) {
            age -= 1
        } else if (month1 == month2) {
            val day1 = now.get(Calendar.DAY_OF_MONTH)
            val day2 = dob.get(Calendar.DAY_OF_MONTH)
            if (day2 > day1) {
                age -= 1
            }
        }

        "$age (${SimpleDateFormat("MMM d, yyyy", Locale.US).format(d)})"
    } catch (ignored: Exception) {
        ""
    }
}