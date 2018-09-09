package com.github.tehras.restapi.movies

import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.restapi.API_KEY
import com.github.tehras.models.tmdb.models.cast.Credits
import com.github.tehras.models.tmdb.models.media.Images
import com.github.tehras.models.tmdb.models.media.Videos
import com.github.tehras.models.tmdb.models.moviedetails.MovieDetails
import com.github.tehras.models.tmdb.models.reviews.Reviews
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@ApplicationScope
interface MoviesService {
    @GET("/3/movie/{movie_id}?api_key=$API_KEY")
    fun movieDetails(@Path("movie_id") id: Long): Single<MovieDetails>

    @GET("/3/movie/{movie_id}/credits?api_key=$API_KEY")
    fun credits(@Path("movie_id") id: Long): Single<Credits>

    @GET("/3/movie/{movie_id}/images?api_key=$API_KEY")
    fun images(@Path("movie_id") id: Long): Single<Images>

    @GET("/3/movie/{movie_id}/videos?api_key=$API_KEY")
    fun videos(@Path("movie_id") id: Long): Single<Videos>

    @GET("/3/movie/{movie_id}/reviews?api_key=$API_KEY")
    fun reviews(@Path("movie_id") id: Long, @Query("page") page: Int = 1): Single<Reviews>
}