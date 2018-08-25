/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.dagger

import android.app.Application
import com.github.tehras.dagger.components.AppComponent
import com.github.tehras.dagger.components.ComponentProvider
import com.github.tehras.dagger.components.DaggerAppComponent

/**
 * @author tkoshkin created on 8/24/18
 */
class DaggerApplication : Application(), ComponentProvider<AppComponent> {
    private val appComponent = lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun getComponent(): AppComponent {
        return appComponent.value
    }
}