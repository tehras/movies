package com.github.tehras.models.tmdb.models.reviews

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reviews(
        @Json(name = "results")
        val reviews: List<Review>,
        @Json(name = "total_pages")
        val totalPages: Int,
        @Json(name = "total_results")
        val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class Review(
        val id: String,
        val author: String,
        val content: String,
        val url: String
)