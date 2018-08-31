package ext.androidx.fragment.app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

/**
 * @author tkoshkin created on 8/28/18
 */

@SuppressLint("SwitchIntDef")
private fun isAppCompatNightModeEnabled(): Boolean = when (AppCompatDelegate.getDefaultNightMode()) {
    AppCompatDelegate.MODE_NIGHT_YES -> true
    AppCompatDelegate.MODE_NIGHT_NO -> false
    else -> false // auto or follow_system, assume it's not the night theme
}

var AppCompatActivity.darkTheme: Boolean
    get() = when ((resources?.configuration?.uiMode ?: 0) and android.content.res.Configuration.UI_MODE_NIGHT_MASK) {
        android.content.res.Configuration.UI_MODE_NIGHT_YES -> true
        android.content.res.Configuration.UI_MODE_NIGHT_NO -> isAppCompatNightModeEnabled() // sometimes it's not night mode, but the night theme is applied because of AppCompat, so trust the default value
        else -> isAppCompatNightModeEnabled() // could be Configuration.UI_MODE_NIGHT_UNDEFINED, check the default value in this case
    }
    set(value) {
        val intValue = if (value) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        if (value != darkTheme) {
            delegate.setLocalNightMode(intValue)
            recreate()
        }

        AppCompatDelegate.setDefaultNightMode(intValue)
    }
