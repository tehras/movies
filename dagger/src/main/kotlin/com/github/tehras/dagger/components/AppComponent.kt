/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.dagger.components

import com.github.tehras.dagger.DaggerApplication
import com.github.tehras.dagger.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author tkoshkin created on 8/24/18
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: DaggerApplication): Builder

        fun build(): AppComponent
    }
}