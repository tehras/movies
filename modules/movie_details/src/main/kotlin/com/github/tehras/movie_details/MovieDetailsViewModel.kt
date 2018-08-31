/*

 */
package com.github.tehras.movie_details

import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.restapi.tmdb.TmdbService
import com.github.tehras.restapi.tmdb.models.moviedetails.MovieDetails
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

        tmdbService.movieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .map {
                    MovieDetailsState(MovieDetailsState.State.DONE, it)
                }
                .doOnError {
                    MovieDetailsState(MovieDetailsState.State.ERROR)
                }
                .startWith(MovieDetailsState(MovieDetailsState.State.LOADING))
                .subscribeUntilDestroyed()
    }
}

data class MovieDetailsState(val state: State, val movieDetails: MovieDetails? = null) {
    enum class State {
        LOADING, DONE, ERROR
    }
}

class MovieDetailsUiEvent