/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui

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
abstract class DiscoverModule {
    @Binds
    @IntoMap
    @ViewModelKey(DiscoverViewModel::class)
    abstract fun bindHomeViewModel(discoverViewModel: DiscoverViewModel): ViewModel
}

@ActivityScope
@Subcomponent(modules = [DiscoverModule::class])
interface DiscoverComponent {
    fun inject(fragment: DiscoverFragment)
}

interface DiscoverComponentCreator : SubComponentCreator {
    fun plusDiscoverComponent(): DiscoverComponent
}