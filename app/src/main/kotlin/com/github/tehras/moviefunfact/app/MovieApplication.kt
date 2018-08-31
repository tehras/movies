package com.github.tehras.moviefunfact.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.github.tehras.dagger.components.ComponentProvider
import com.github.tehras.dagger.components.DaggerApplication
import com.github.tehras.logger.BuildConfig
import com.github.tehras.logger.CrashReportingTree
import ext.android.content.isDarkModeEnabled
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


/**
 * @author tkoshkin created on 8/24/18
 */
class MovieApplication : Application(), DaggerApplication, ComponentProvider<AppComponent> {

    private lateinit var appComponent: AppComponent


    override fun getComponent(): AppComponent {
        return appComponent
    }

    @Inject
    lateinit var sharedPrefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        appComponent.plusApplication(this)

        if (sharedPrefs.isDarkModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}