/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.home

import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.restapi.tmdb.Discover
import com.github.tehras.restapi.tmdb.TmdbService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author tkoshkin created on 8/24/18
 */
class HomeViewModel @Inject constructor(private val tmdbServie: TmdbService) : ObservableViewModel<HomeViewState, HomeViewUi>() {
    private val apiDisposable: CompositeDisposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()

    }

    override fun onCreate() {
        super.onCreate()

        tmdbServie.discover()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .map { HomeViewState(hashMapOf(2018 to it)) }
            .subscribeUntilDestroyed()
    }

    override fun onDestroy() {
        apiDisposable.dispose()

        super.onDestroy()
    }

}

data class HomeViewState(val discoverMap: HashMap<Int, List<Discover>> /* <Year, List of Discovers> */, val error: Boolean = false)
class HomeViewUi