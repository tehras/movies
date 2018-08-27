/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.restapi.tmdb

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Arrays

/**
 * @author tkoshkin created on 8/26/18
 */
@JsonClass(generateAdapter = true)
data class DiscoverResponse(
    val page: Int,
    val results: List<Discover>,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int
)

@JsonClass(generateAdapter = true)
data class Discover(
    @Json(name = "poster_path") val posterPath: String?,
    val adult: Boolean,
    val overview: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "genre_ids") val genreIds: Array<Int>,
    val id: Int,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "original_language") val originalLanguage: String,
    val title: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    val popularity: Double,
    @Json(name = "vote_count") val voteCount: Int,
    val video: Boolean,
    @Json(name = "vote_average") val voteAverage: Double

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Discover

        if (posterPath != other.posterPath) return false
        if (adult != other.adult) return false
        if (overview != other.overview) return false
        if (releaseDate != other.releaseDate) return false
        if (!Arrays.equals(genreIds, other.genreIds)) return false
        if (id != other.id) return false
        if (originalTitle != other.originalTitle) return false
        if (originalLanguage != other.originalLanguage) return false
        if (title != other.title) return false
        if (backdropPath != other.backdropPath) return false
        if (popularity != other.popularity) return false
        if (voteCount != other.voteCount) return false
        if (video != other.video) return false
        if (voteAverage != other.voteAverage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = posterPath?.hashCode() ?: 0
        result = 31 * result + adult.hashCode()
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + Arrays.hashCode(genreIds)
        result = 31 * result + id
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + popularity.hashCode()
        result = 31 * result + voteCount
        result = 31 * result + video.hashCode()
        result = 31 * result + voteAverage.hashCode()
        return result
    }
}