package com.github.tehras.restapi.tmdb.models.genres

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author tkoshkin created on 8/28/18
 */
@JsonClass(generateAdapter = true)
data class GenresResponse(@Json(name = "genres") val genres: List<Genre>)

@JsonClass(generateAdapter = true)
data class Genre(val id: Int, val name: String)