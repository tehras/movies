package com.github.tehras.person

import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.restapi.tmdb.models.cast.Person
import com.github.tehras.restapi.tmdb.person.PersonService
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonViewModel @Inject constructor(private val personId: Long, private val personService: PersonService) : ObservableViewModel<PersonState, PersonUiEvent>() {

    override fun onCreate() {
        super.onCreate()

        personService
                .person(personId)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .map { PersonState(it, PersonState.State.SUCCESS) }
                .onErrorReturn { PersonState(null, PersonState.State.ERROR) }
                .startWith(PersonState(null, PersonState.State.LOADING))
                .subscribeUntilDestroyed()
    }

}

data class PersonState(val person: Person?, val state: State) {
    enum class State { LOADING, ERROR, SUCCESS }
}

class PersonUiEvent