package com.github.tehras.restapi.tmdb.models.cast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Credits(
        val id: Long,
        val cast: List<Cast>,
        val crew: List<Crew>
)

@JsonClass(generateAdapter = true)
data class Cast(
        @Json(name = "cast_id")
        val castId: Long,
        val character: String,
        @Json(name = "credit_id")
        val creditId: String,
        val gender: Int?,
        val id: Long,
        val name: String,
        val order: Int,
        @Json(name = "profile_path")
        val profilePath: String?
)

@JsonClass(generateAdapter = true)
data class Crew(
        @Json(name = "credit_id")
        val creditId: String,
        val department: String,
        val job: String,
        val gender: Int?,
        val id: Long,
        val name: String,
        @Json(name = "profile_path")
        val profilePath: String?
)