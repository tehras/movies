package com.github.tehras.boxoffice

import androidx.lifecycle.ViewModel
import com.github.tehras.arch.dagger.ViewModelFactoryModule
import com.github.tehras.arch.dagger.ViewModelKey
import com.github.tehras.dagger.components.SubComponentCreator
import com.github.tehras.dagger.scopes.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap


/**
 * @author tkoshkin created on 8/24/18
 */
@Module(includes = [ViewModelFactoryModule::class])
abstract class BoxOfficeModule {
    @Binds
    @IntoMap
    @ViewModelKey(BoxOfficeViewModel::class)
    abstract fun bindeBoxOfficeViewModel(boxOfficeViewModel: BoxOfficeViewModel): ViewModel
}

@ActivityScope
@Subcomponent(modules = [BoxOfficeModule::class])
interface BoxOfficeComponent {
    fun inject(fragment: BoxOfficeFragment)
}

interface BoxOfficeComponentCreator : SubComponentCreator {
    fun plusBoxOfficeComponent(): BoxOfficeComponent
}