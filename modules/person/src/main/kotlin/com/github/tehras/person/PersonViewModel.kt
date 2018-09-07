package com.github.tehras.person

import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.restapi.tmdb.models.cast.Images
import com.github.tehras.restapi.tmdb.models.cast.Person
import com.github.tehras.restapi.tmdb.models.cast.PersonCast
import com.github.tehras.restapi.tmdb.person.PersonService
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonViewModel @Inject constructor(private val personId: Long, private val personService: PersonService) : ObservableViewModel<PersonState, PersonUiEvent>() {

    override fun onCreate() {
        super.onCreate()

        val imagesObservable = personService
                .images(personId)
                .toObservable()
                .startWith(Images(mutableListOf()))

        val personObservable = personService
                .person(personId)
                .toObservable()

        val movieCreditsObservable = personService
                .movieCredits(personId)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .map { personCred ->
                    personCred.cast
                            .filter { it.character.isNotEmpty() }
                            .sortedByDescending { it.releaseDateConverted }
                            .toMutableList()
                }
                .subscribeOn(Schedulers.io())
                .startWith(mutableListOf<PersonCast>())

        Observables
                .combineLatest(personObservable, imagesObservable, movieCreditsObservable)
                .subscribeOn(Schedulers.io())
                .map {
                    PersonState(PersonState.State.SUCCESS, it.first, it.second, it.third)
                }
                .onErrorReturn { PersonState(PersonState.State.ERROR) }
                .startWith(PersonState(PersonState.State.LOADING))
                .subscribeUntilDestroyed()
    }

}

data class PersonState(val state: State, val person: Person? = null, val images: Images = Images(mutableListOf()), val movieCredits: MutableList<PersonCast> = mutableListOf()) {
    enum class State { LOADING, ERROR, SUCCESS }
}

class PersonUiEvent