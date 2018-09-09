package com.github.tehras.restapi.person

import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.restapi.API_KEY
import com.github.tehras.models.tmdb.models.cast.Images
import com.github.tehras.models.tmdb.models.cast.Person
import com.github.tehras.models.tmdb.models.cast.PersonCredits
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

@ApplicationScope
interface PersonService {
    @GET("/3/person/{person_id}?api_key=$API_KEY")
    fun person(@Path("person_id") id: Long): Single<Person>

    @GET("/3/person/{person_id}/images?api_key=$API_KEY")
    fun images(@Path("person_id") id: Long): Single<Images>

    @GET("/3/person/{person_id}/movie_credits?api_key=$API_KEY")
    fun movieCredits(@Path("person_id") id: Long): Single<PersonCredits>

    @GET("/3/person/{person_id}/tv_credits?api_key=$API_KEY")
    fun tvCredits(@Path("person_id") id: Long): Single<PersonCredits>
}