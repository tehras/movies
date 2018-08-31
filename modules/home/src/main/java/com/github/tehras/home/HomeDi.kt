package com.github.tehras.home

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
abstract class HomeModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}

@ActivityScope
@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    fun inject(homeActivity: HomeActivity)
}

interface HomeComponentCreator : SubComponentCreator {
    fun plusHomeActivity(): HomeComponent
}