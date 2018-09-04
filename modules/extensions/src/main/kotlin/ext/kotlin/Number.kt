package ext.kotlin

import java.text.NumberFormat

fun Number.formatWithCommas(): String {
    return NumberFormat.getIntegerInstance().format(this.toInt())
}

