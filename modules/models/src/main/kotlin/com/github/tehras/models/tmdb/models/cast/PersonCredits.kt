package com.github.tehras.models.tmdb.models.cast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ext.kotlin.toDateObject

@JsonClass(generateAdapter = true)
data class PersonCredits(
        val id: Long,
        val cast: List<PersonCast>
)


@JsonClass(generateAdapter = true)
data class PersonCast(
        @Json(name = "credit_id")
        val creditId: String,
        @Json(name = "release_date")
        val releaseDate: String?,
        val character: String,
        val title: String?,
        val overview: String,
        @Json(name = "vote_average")
        val voteAverage: Double?,
        @Json(name = "vote_count")
        val voteCount: Int?,
        val gender: Int?,
        val id: Long,
        @Json(name = "profile_path")
        val profilePath: String?,
        @Json(name = "poster_path")
        val posterPath: String?
) {
    val releaseDateConverted by lazy { releaseDate?.toDateObject() }
}