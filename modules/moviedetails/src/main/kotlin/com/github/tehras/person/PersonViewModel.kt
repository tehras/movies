package com.github.tehras.person

import android.annotation.SuppressLint
import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.models.tmdb.models.cast.Images
import com.github.tehras.models.tmdb.models.cast.Person
import com.github.tehras.models.tmdb.models.cast.PersonCast
import com.github.tehras.restapi.person.PersonService
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PersonViewModel @Inject constructor(
        private val personId: Long,
        private val personService: PersonService
) : ObservableViewModel<PersonState, PersonUiEvent>() {

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        val imagesObservable = personService
                .images(personId)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .startWith(Images(mutableListOf()))

        val personObservable = personService
                .person(personId)
                .toObservable()
                .subscribeOn(Schedulers.io())

        val movieCreditsObservable = personService
                .movieCredits(personId)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .map { personCred ->
                    personCred.cast
                            .asSequence()
                            .filter { it.character.isNotEmpty() }
                            .toMutableList()
                }
                .doOnError {
                    Timber.w("Error getting movie credits $it")
                }
                .observeOn(Schedulers.io())

        val tvCreditsObservable = personService
                .tvCredits(personId)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .map { personCred ->
                    personCred.cast
                            .asSequence()
                            .filter { it.character.isNotEmpty() }
                            .toMutableList()
                }
                .doOnError {
                    Timber.w("Error getting TV credits $it")
                }
                .observeOn(Schedulers.io())

        val creditsObservable =
                Observables.zip(movieCreditsObservable, tvCreditsObservable)
                        .subscribeOn(Schedulers.io())
                        .map {
                            val tags = mutableListOf<Credits.Tags>()
                            if (it.first.isNotEmpty()) {
                                tags.add(Credits.Tags.MOVIES)
                            }
                            if (it.second.isNotEmpty()) {
                                tags.add(Credits.Tags.TV)
                            }
                            Credits(
                                    it.first,
                                    it.second,
                                    mutableListOf(),
                                    tags
                            ).apply {
                                updateSelectedCredits()
                            }
                        }

        val latestStateObservables = Observables
                .combineLatest(personObservable, imagesObservable, creditsObservable)
                .map {
                    PersonState(PersonState.State.SUCCESS, it.first, it.second, it.third)
                }
                .onErrorReturn { PersonState(PersonState.State.ERROR) }
                .startWith(PersonState(PersonState.State.LOADING))
                .subscribeOn(Schedulers.io())

        val tagUiObservable = uiEvents()
                .ofType(PersonUiEvent.TagUpdate::class.java)
                .startWith(PersonUiEvent.TagUpdate(Credits.Tags.MOVIES, true))
                .subscribeOn(Schedulers.io())

        Observables
                .combineLatest(tagUiObservable, latestStateObservables)
                .map {
                    it.second.apply {
                        credits.updateTag(it.first.tag, it.first.checked)
                    }.copy()
                }
                .subscribeOn(Schedulers.io())
                .subscribeUntilDestroyed()
    }

}

data class PersonState(
        val state: State,
        val person: Person? = null,
        val images: Images = Images(mutableListOf()),
        val credits: Credits = Credits()
) {
    enum class State { LOADING, ERROR, SUCCESS }
}

data class Credits(
        private val movieCredits: MutableList<PersonCast> = mutableListOf(),
        private val tvCredits: MutableList<PersonCast> = mutableListOf(),
        val selectedCredits: MutableList<PersonCast> = mutableListOf(),
        val tags: MutableList<Tags> = mutableListOf(Tags.TV, Tags.MOVIES)
) {
    fun isNotEmpty(): Boolean = movieCredits.isNotEmpty() || tvCredits.isNotEmpty()
    fun updateTag(tag: Tags, checked: Boolean) {
        tags.firstOrNull { it == tag && checked != it.selected }?.let {
            it.selected = checked
            updateSelectedCredits()
        }
    }

    fun updateSelectedCredits() {
        selectedCredits.clear()
        tags
                .filter { it.selected }
                .forEach {
                    when (it) {
                        Tags.TV -> selectedCredits.addAll(tvCredits)
                        Tags.MOVIES -> selectedCredits.addAll(movieCredits)
                    }
                }

        selectedCredits
                .sortByDescending { it.releaseDateLong }
    }

    enum class Tags(val displayName: String, var selected: Boolean) {
        TV("TV", true), MOVIES("Movies", true)
    }
}

sealed class PersonUiEvent {
    data class TagUpdate(val tag: Credits.Tags, val checked: Boolean) : PersonUiEvent()
}