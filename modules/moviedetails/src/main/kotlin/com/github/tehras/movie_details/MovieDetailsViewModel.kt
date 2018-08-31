/*

 */
package com.github.tehras.movie_details

import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.restapi.tmdb.TmdbService
import com.github.tehras.restapi.tmdb.models.cast.Cast
import com.github.tehras.restapi.tmdb.models.moviedetails.MovieDetails
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author tkoshkin created on 8/29/18
 */
class MovieDetailsViewModel @Inject constructor(
        private val tmdbService: TmdbService,
        private val movieId: Long
) : ObservableViewModel<MovieDetailsState, MovieDetailsUiEvent>() {

    override fun onCreate() {
        super.onCreate()

        val creditsObservable = tmdbService.credits(movieId)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .doOnError {
                    mutableListOf<Cast>()
                }
                .startWith(mutableListOf())

        val movieDetailsObservable = tmdbService.movieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .toObservable()

        Observables.combineLatest(movieDetailsObservable, creditsObservable)
                .doOnError {
                    MovieDetailsState(MovieDetailsState.State.ERROR, null, mutableListOf())
                }
                .map {
                    MovieDetailsState(MovieDetailsState.State.DONE, it.first, it.second.cast.toMutableList())
                }
                .startWith(MovieDetailsState(MovieDetailsState.State.LOADING, null, mutableListOf()))
                .subscribeUntilDestroyed()
    }
}

data class MovieDetailsState(val state: State, val movieDetails: MovieDetails? = null, val cast: MutableList<Cast>) {
    enum class State {
        LOADING, DONE, ERROR
    }
}

class MovieDetailsUiEvent