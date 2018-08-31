/*

 */
package com.github.tehras.movie_details

import androidx.lifecycle.ViewModel
import com.github.tehras.arch.dagger.ViewModelFactoryModule
import com.github.tehras.arch.dagger.ViewModelKey
import com.github.tehras.dagger.components.SubComponentCreator
import com.github.tehras.dagger.scopes.ActivityScope
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

/**
 * @author tkoshkin created on 8/29/18
 */
@Module(includes = [ViewModelFactoryModule::class])
abstract class MovieDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(movieDetailsViewModel: MovieDetailsViewModel): ViewModel
}

@ActivityScope
@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsComponent {

    fun inject(activity: MovieDetailsActivity)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun movieId(id: Long): Builder
        fun build(): MovieDetailsComponent
    }
}

interface MovieDetailsComponentCreator : SubComponentCreator {
    fun plusMovieDetailsActivity(): MovieDetailsComponent.Builder
}