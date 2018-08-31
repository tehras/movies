


package com.github.tehras.restapi.tmdb

/**
 * @author tkoshkin created on 8/26/18
 */
enum class SortBy(private val value: String) {
    POPULARITY_DESC("popularity.desc");

    override fun toString(): String {
        return value
    }
}