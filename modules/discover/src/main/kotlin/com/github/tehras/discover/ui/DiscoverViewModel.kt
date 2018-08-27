/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui

import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.restapi.tmdb.Discover
import com.github.tehras.restapi.tmdb.TmdbService
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author tkoshkin created on 8/26/18
 */
class DiscoverViewModel @Inject constructor(private val tmdbService: TmdbService) : ObservableViewModel<DiscoverState, DiscoverUiView>() {

    override fun onCreate() {
        super.onCreate()

        tmdbService.discover()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .map { it.results }
            .map { DiscoverState(hashMapOf(2018 to it)) }
            .subscribeUntilDestroyed()
    }
}

class DiscoverState(val discoverMap: HashMap<Int, List<Discover>> /* <Year, List of Discovers> */, val error: Boolean = false)
class DiscoverUiView