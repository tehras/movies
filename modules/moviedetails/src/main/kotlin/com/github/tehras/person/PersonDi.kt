package com.github.tehras.person

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

@Module(includes = [ViewModelFactoryModule::class])
abstract class PersonModule {
    @Binds
    @IntoMap
    @ViewModelKey(PersonViewModel::class)
    abstract fun bindPersonViewModel(personViewModel: PersonViewModel): ViewModel
}

@ActivityScope
@Subcomponent(modules = [PersonModule::class])
interface PersonComponent {

    fun inject(activity: PersonActivity)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun personId(id: Long): Builder

        fun build(): PersonComponent
    }
}

interface PersonComponentCreator : SubComponentCreator {
    fun plusPersonActivity(): PersonComponent.Builder
}