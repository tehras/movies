package ext.android.content

import android.content.SharedPreferences

/**
 * @author tkoshkin created on 8/28/18
 */
fun SharedPreferences.isDarkModeEnabled(): Boolean {
    return this.getBoolean("dark_mode_enabled", false)
}

fun SharedPreferences.setDarkModeEnabled(enabled: Boolean) {
    this.edit().putBoolean("dark_mode_enabled", enabled).apply()
}