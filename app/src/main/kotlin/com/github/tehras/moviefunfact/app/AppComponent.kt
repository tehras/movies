package com.github.tehras.moviefunfact.app

import android.app.Application
import com.github.tehras.boxoffice.BoxOfficeComponentCreator
import com.github.tehras.dagger.components.MainComponent
import com.github.tehras.dagger.modules.AppModule
import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.discover.ui.DiscoverComponentCreator
import com.github.tehras.home.HomeComponentCreator
import com.github.tehras.moshi.MoshiModule
import com.github.tehras.movie_details.MovieDetailsComponentCreator
import com.github.tehras.restapi.retrofit.RetrofitModule
import com.github.tehras.restapi.tmdb.TmdbServiceModule
import dagger.BindsInstance
import dagger.Component

/**
 * @author tkoshkin created on 8/24/18
 */
@Suppress("unused")
@ApplicationScope
@Component(
        modules = [
            AppModule::class,
            RetrofitModule::class,
            TmdbServiceModule::class,
            MoshiModule::class
        ]
)
interface AppComponent :
        MainComponent,
        HomeComponentCreator,
        MovieDetailsComponentCreator,
        BoxOfficeComponentCreator,
        DiscoverComponentCreator {

    fun plusApplication(application: MovieApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}