package ext.android.widget

import android.widget.TextView


/**
 * @author tkoshkin created on 8/29/18
 */
fun TextView.isEllipsized(): Boolean {
    val l = this.layout
    if (l != null) {
        val lines = l.lineCount
        if (lines > 0) {
            if (l.getEllipsisCount(lines - 1) > 0) {
                return true
            }
        }
    }

    return false
}