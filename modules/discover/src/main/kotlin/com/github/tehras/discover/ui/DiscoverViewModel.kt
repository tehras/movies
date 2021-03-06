package com.github.tehras.discover.ui

import android.annotation.SuppressLint
import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.models.tmdb.models.common.Movie
import com.github.tehras.models.tmdb.models.genres.Genre
import com.github.tehras.models.tmdb.models.genres.GenresResponse
import com.github.tehras.restapi.discover.SortBy
import com.github.tehras.restapi.discover.TmdbService
import ext.java.util.format
import ext.java.util.monthsAgo
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * @author tkoshkin created on 8/26/18
 */
class DiscoverViewModel @Inject constructor(private val tmdbService: TmdbService) : ObservableViewModel<DiscoverState, DiscoverUiView>() {

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        val genres = tmdbService.genres()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .doOnError {
                    GenresResponse(listOf())
                }

        val discoverObservable = tmdbService
                .discover(
                        releaseDateGte = Calendar
                                .getInstance()
                                .monthsAgo(6)
                                .format(),
                        releaseDateLte = Calendar
                                .getInstance()
                                .format(),
                        sortBy = SortBy.POPULARITY_DESC
                )
                .subscribeOn(Schedulers.io())
                .map {
                    it.results.toMutableList()
                }
                .doOnError {
                    mutableListOf<Movie>()
                }
                .toObservable()

        Observables.zip(discoverObservable, genres)
                .map { pair ->
                    pair.first.forEach {
                        it.genres.addAll(findGenres(it.genreIds, pair.second))
                    }
                    DiscoverState(pair.first, false)
                }
                .onErrorReturn { DiscoverState(mutableListOf(), true) }
                .subscribeUntilDestroyed()
    }

    private fun findGenres(genreIds: Array<Int>, genresResponse: GenresResponse): MutableList<Genre> {
        if (genresResponse.genres.isEmpty()) {
            return mutableListOf()
        }

        val genres = mutableListOf<Genre>()
        genreIds.forEach { id ->
            genresResponse.genres.firstOrNull { it.id == id }?.also { genre ->
                genres.add(genre)
            }
        }

        return genres
    }
}

data class DiscoverState(
        val discoverItems: MutableList<Movie> /* <Year, List of Discovers> */,
        val error: Boolean = false
)

class DiscoverUiView