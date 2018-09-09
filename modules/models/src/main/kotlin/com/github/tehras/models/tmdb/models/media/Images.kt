package com.github.tehras.models.tmdb.models.media

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
        val posters: List<Poster>,
        val backdrops: List<Backdrop>
)

@JsonClass(generateAdapter = true)
data class Poster(
        @Json(name = "aspect_ratio")
        val aspectRation: Double,
        @Json(name = "file_path")
        val filePath: String,
        val height: Double,
        @Json(name = "vote_average")
        val voteAverage: Int,
        @Json(name = "vote_count")
        val voteCount: Int,
        val width: Int
)

@JsonClass(generateAdapter = true)
data class Backdrop(
        @Json(name = "aspect_ratio")
        val aspectRation: Double,
        @Json(name = "file_path")
        val filePath: String,
        val height: Double,
        @Json(name = "vote_average")
        val voteAverage: Int,
        @Json(name = "vote_count")
        val voteCount: Int,
        val width: Int
)