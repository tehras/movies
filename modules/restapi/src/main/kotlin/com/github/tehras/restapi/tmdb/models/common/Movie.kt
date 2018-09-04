package com.github.tehras.restapi.tmdb.models.common

import com.github.tehras.restapi.tmdb.models.genres.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
open class Movie(
        @Json(name = "poster_path") val posterPath: String?,
        val adult: Boolean,
        val overview: String?,
        @Json(name = "release_date") val releaseDate: String?,
        @Json(name = "genre_ids") val genreIds: Array<Int>,
        val id: Long,
        @Json(name = "original_title") val originalTitle: String,
        @Json(name = "original_language") val originalLanguage: String,
        val title: String,
        @Json(name = "backdrop_path") val backdropPath: String?,
        val popularity: Double,
        @Json(name = "vote_count") val voteCount: Int,
        val video: Boolean,
        @Json(name = "vote_average") val voteAverage: Double,
        @Transient val genres: MutableList<Genre> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

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
        if (genres != other.genres) return false

        return true
    }

    override fun hashCode(): Int {
        var result = posterPath?.hashCode() ?: 0
        result = 31 * result + adult.hashCode()
        result = 31 * result + (overview?.hashCode() ?: 0)
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + Arrays.hashCode(genreIds)
        result = 31 * result + id.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + popularity.hashCode()
        result = 31 * result + voteCount
        result = 31 * result + video.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + genres.hashCode()
        return result
    }
}