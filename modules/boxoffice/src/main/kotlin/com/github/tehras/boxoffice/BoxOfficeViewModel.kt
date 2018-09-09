package com.github.tehras.boxoffice

import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.models.tmdb.models.common.Movie
import com.github.tehras.restapi.boxoffice.BoxOfficeService
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BoxOfficeViewModel @Inject constructor(private val boxOfficeService: BoxOfficeService) : ObservableViewModel<BoxOfficeState, BoxOfficeUi>() {

    override fun onCreate() {
        super.onCreate()

        boxOfficeService
                .nowPlaying()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .map {
                    BoxOfficeState(BoxOfficeState.State.DONE, it.results.toMutableList())
                }
                .startWith(BoxOfficeState(BoxOfficeState.State.LOADING, mutableListOf()))
                .doOnError { BoxOfficeState(BoxOfficeState.State.ERROR, mutableListOf()) }
                .subscribeUntilDestroyed()
    }
}

data class BoxOfficeState(
        val state: State,
        val boxOfficeMovies: MutableList<Movie>
) {
    enum class State {
        LOADING, DONE, ERROR
    }
}

sealed class BoxOfficeUi