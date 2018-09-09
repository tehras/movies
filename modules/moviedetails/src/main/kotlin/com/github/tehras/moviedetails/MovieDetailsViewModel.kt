/*

 */
package com.github.tehras.moviedetails

import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.models.tmdb.models.cast.Cast
import com.github.tehras.models.tmdb.models.moviedetails.MovieDetails
import com.github.tehras.models.tmdb.models.reviews.Review
import com.github.tehras.restapi.movies.MoviesService
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author tkoshkin created on 8/29/18
 */
class MovieDetailsViewModel @Inject constructor(
        private val tmdbService: MoviesService,
        private val movieId: Long
) : ObservableViewModel<MovieDetailsState, MovieDetailsUiEvent>() {

    override fun onCreate() {
        super.onCreate()

        val creditsObservable =
                tmdbService.credits(movieId)
                        .subscribeOn(Schedulers.io())
                        .toObservable()
                        .doOnError {
                            mutableListOf<Cast>()
                        }
                        .startWith(mutableListOf())

        val reviewsObservable =
                tmdbService.reviews(movieId)
                        .subscribeOn(Schedulers.io())
                        .toObservable()
                        .doOnError {
                            mutableListOf<Review>()
                        }
                        .startWith(mutableListOf())

        val movieDetailsObservable =
                tmdbService.movieDetails(movieId)
                        .subscribeOn(Schedulers.io())
                        .toObservable()

        Observables.combineLatest(movieDetailsObservable, creditsObservable, reviewsObservable)
                .doOnError {
                    MovieDetailsState(MovieDetailsState.State.ERROR, null, mutableListOf(), mutableListOf())
                }
                .map {
                    MovieDetailsState(MovieDetailsState.State.DONE, it.first, it.second.cast.toMutableList(), it.third.reviews.toMutableList())
                }
                .startWith(MovieDetailsState(MovieDetailsState.State.LOADING, null, mutableListOf(), mutableListOf()))
                .subscribeUntilDestroyed()
    }
}

data class MovieDetailsState(val state: State, val movieDetails: MovieDetails? = null, val cast: MutableList<Cast>, val reviews: MutableList<Review>) {
    enum class State {
        LOADING, DONE, ERROR
    }
}

class MovieDetailsUiEvent