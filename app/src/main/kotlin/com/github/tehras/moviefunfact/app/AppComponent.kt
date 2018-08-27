/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.moviefunfact.app

import com.github.tehras.dagger.components.MainComponent
import com.github.tehras.dagger.modules.AppModule
import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.home.HomeComponentCreator
import com.github.tehras.moshi.MoshiModule
import com.github.tehras.restapi.retrofit.RetrofitModule
import com.github.tehras.restapi.tmdb.TmdbServiceModule
import dagger.BindsInstance
import dagger.Component

/**
 * @author tkoshkin created on 8/24/18
 */
@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        RetrofitModule::class,
        TmdbServiceModule::class,
        MoshiModule::class
    ]
)
interface AppComponent : MainComponent, HomeComponentCreator {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: MovieApplication): Builder

        fun build(): AppComponent
    }
}