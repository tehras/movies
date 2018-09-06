package com.github.tehras.restapi.tmdb.models.cast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Person(
        val birthday: String?,
        val deathday: String?,
        @Json(name = "known_for_department")
        val knownForDepartment: String,
        val id: Long,
        val name: String,
        @Json(name = "also_known_as")
        val aka: List<String>,
        val gender: Int,
        val biography: String,
        val popularity: String,
        @Json(name = "place_of_birth")
        val placeOfBirth: String?,
        @Json(name = "profile_path")
        val profilePath: String,
        val adult: Boolean,
        @Json(name = "imdb_id")
        val imdbId: String,
        val homepage: String?
)