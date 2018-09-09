package com.github.tehras.models.tmdb.models.discover

import com.github.tehras.models.tmdb.models.common.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author tkoshkin created on 8/26/18
 */
@JsonClass(generateAdapter = true)
data class DiscoverResponse(
        val page: Int,
        val results: List<Movie>,
        @Json(name = "total_results") val totalResults: Int,
        @Json(name = "total_pages") val totalPages: Int
)