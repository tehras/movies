/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.discover.ui

import android.annotation.SuppressLint
import com.github.tehras.arch.ObservableViewModel
import com.github.tehras.restapi.tmdb.Discover
import com.github.tehras.restapi.tmdb.TmdbService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

/**
 * @author tkoshkin created on 8/26/18
 */
class DiscoverViewModel @Inject constructor(private val tmdbService: TmdbService) : ObservableViewModel<DiscoverState, DiscoverUiView>() {

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        val listOfDiscoverRequests = mutableListOf<Observable<MutableList<Discover>>>()
        for (year in 2018 downTo 2008) {
            listOfDiscoverRequests += tmdbService.discover(dateByYear(year))
                .toObservable()
                .subscribeOn(Schedulers.io())
                .map { it.results.toMutableList() }
                .doOnError { mutableListOf<Discover>() }
        }

        Observable.merge(listOfDiscoverRequests)
            .toList()
            .map {
                val mapOfDiscoveries = mutableMapOf<Int, MutableList<Discover>>()
                it.forEach { listOfDiscover ->
                    mapOfDiscoveries[listOfDiscover.getOrNull(0)?.releaseDate?.getYear() ?: 0] = listOfDiscover
                }
                DiscoverState(mapOfDiscoveries.toSortedMap(compareByDescending { key -> key }))
            }
            .toObservable()
            .subscribeUntilDestroyed()
    }

    private fun dateByYear(year: Int): String {
        val c = Calendar.getInstance()
        return "$year-${c.get(Calendar.MONTH)}-${c.get(Calendar.DAY_OF_MONTH)}"
    }
}

private fun String.getYear(): Int? {
    val d = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this)
    return Calendar.getInstance().let { c ->
        c.time = d
        c.get(Calendar.YEAR)
    }
}

class DiscoverState(val discoverMap: MutableMap<Int, MutableList<Discover>> /* <Year, List of Discovers> */, val error: Boolean = false)
class DiscoverUiView