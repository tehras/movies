package com.github.tehras.restapi.tmdb.models.cast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Images(
        val profiles: List<Image>
)

@JsonClass(generateAdapter = true)
data class Image(
        @Json(name = "aspect_ratio")
        val aspectRatio: Double,
        @Json(name = "file_path")
        val filePath: String,
        val height: Int,
        @Json(name = "vote_average")
        val voteAverage: Double,
        @Json(name = "vote_count")
        val voteCount: Int,
        val width: Int
)