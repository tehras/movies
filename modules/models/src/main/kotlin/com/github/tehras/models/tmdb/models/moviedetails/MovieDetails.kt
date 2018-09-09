package com.github.tehras.models.tmdb.models.moviedetails

import com.github.tehras.models.tmdb.models.genres.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * @author tkoshkin created on 8/29/18
 */
@JsonClass(generateAdapter = true)
data class MovieDetails(
        val adult: Boolean,
        @Json(name = "backdrop_path")
        val backdropPath: String?,
        val budget: Long,
        val genres: List<Genre>,
        val homepage: String?,
        val id: Long,
        @Json(name = "imdb_id")
        val imdbId: String?,
        @Json(name = "original_language")
        val originalLanguage: String,
        @Json(name = "original_title")
        val originalTitle: String,
        val overview: String?,
        val popularity: Double?,
        @Json(name = "poster_path")
        val posterPath: String?,
        @Json(name = "release_date")
        val releaseDate: String,
        val revenue: Long,
        val runtime: Long?,
        val status: Status,
        val tagline: String?,
        val title: String,
        val video: Boolean,
        @Json(name = "vote_average")
        val voteAverage: Double,
        @Json(name = "vote_count")
        val voteCount: Double,
        @Json(name = "production_companies")
        val productionCompanies: List<ProductionCompany>,
        @Json(name = "production_countries")
        val productionCountries: List<Country>,
        @Json(name = "spoken_languages")
        val spokenLanguages: List<Language>
)

@JsonClass(generateAdapter = true)
data class ProductionCompany(
        val name: String,
        val id: Int,
        @Json(name = "logo_path")
        val logoPath: String?,
        @Json(name = "origin_country")
        val originCountry: String
)

@JsonClass(generateAdapter = true)
data class Language(
        @Json(name = "iso_639_1")
        val iso: String,
        val name: String
)

@JsonClass(generateAdapter = true)
data class Country(
        @Json(name = "iso_3166_1")
        val iso: String,
        val name: String
)

enum class Status {
    @Json(name = "Rumored")
    RUMORED,
    @Json(name = "Planned")
    PLANNED,
    @Json(name = "In Production")
    IN_PRODUCTION,
    @Json(name = "Pst Production")
    POST_PRODUCTION,
    @Json(name = "Released")
    RELEASED,
    @Json(name = "Canceled")
    CANCELED
}