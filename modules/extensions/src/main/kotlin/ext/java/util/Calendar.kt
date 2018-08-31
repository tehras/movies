


package ext.java.util

import java.util.Calendar

/**
 * @author tkoshkin created on 8/28/18
 */
fun Calendar.monthsAgo(months: Int): Calendar {
    this.add(Calendar.MONTH, -months)

    return this
}

fun Calendar.format(): String = "${this.get(Calendar.YEAR)}-${this.get(Calendar.MONTH)}-${this.get(Calendar.DAY_OF_MONTH)}"