package com.github.tehras.restapi.tmdb.models.media

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Videos {

}

@JsonClass(generateAdapter = true)
data class Video(
        val id: String,
        val key: String,
        val name: String,
        val site: String,
        val size: Int,
        val type: Type
)

enum class Type {
    @Json(name = "Trailer")
    TRAILER,
    @Json(name = "Teaser")
    TEASER,
    @Json(name = "Clip")
    CLIP,
    @Json(name = "Featurette")
    FEATURETTE,
}