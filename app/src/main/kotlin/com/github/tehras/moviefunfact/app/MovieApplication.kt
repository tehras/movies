/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.moviefunfact.app

import android.app.Application
import com.github.tehras.dagger.components.ComponentProvider
import com.github.tehras.dagger.components.DaggerApplication
import com.github.tehras.logger.BuildConfig
import com.github.tehras.logger.CrashReportingTree
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * @author tkoshkin created on 8/24/18
 */
class MovieApplication : Application(), DaggerApplication, ComponentProvider<AppComponent> {

    private val appComponent = lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun getComponent(): AppComponent {
        return appComponent.value
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}