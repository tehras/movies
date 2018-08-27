/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.restapi.tmdb

import com.github.tehras.dagger.scopes.ApplicationScope
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Calendar
import javax.inject.Singleton

/**
 * @author tkoshkin created on 8/26/18
 */
@ApplicationScope
interface TmdbService {
    @GET("/3/discover/movie?api_key=$API_KEY")
    fun discover(
        @Query(value = "primary_release_date.gte") releaseDateGte: String = today(),
        @Query(value = "primary_release_date.lte") releaseDateLte: String = releaseDateGte,
        @Query(value = "sort_by") sortBy: SortBy = SortBy.POPULARITY_DESC
    ): Single<DiscoverResponse>
}

private fun today(): String {
    val c = Calendar.getInstance()
    return "${c.get(Calendar.YEAR)}-${c.get(Calendar.MONTH)}-${c.get(Calendar.DAY_OF_MONTH)}"
}