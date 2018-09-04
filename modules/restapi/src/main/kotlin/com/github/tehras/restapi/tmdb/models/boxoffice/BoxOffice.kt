package com.github.tehras.restapi.tmdb.models.boxoffice

import com.github.tehras.restapi.tmdb.models.common.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BoxOffice(
        val results: List<Movie>,
        @Json(name = "total_pages")
        val totalPages: Int,
        @Json(name = "total_results")
        val totalResults: Int
)

