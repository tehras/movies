package com.github.tehras.restapi.tmdb.person

import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.restapi.tmdb.API_KEY
import com.github.tehras.restapi.tmdb.models.cast.Images
import com.github.tehras.restapi.tmdb.models.cast.Person
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

@ApplicationScope
interface PersonService {
    @GET("/3/person/{person_id}?api_key=$API_KEY")
    fun person(@Path("person_id") id: Long): Single<Person>

    @GET("/3/person/{person_id}/images?api_key=$API_KEY")
    fun images(@Path("person_id") id: Long): Single<Images>
}