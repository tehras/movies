


package com.github.tehras.restapi.tmdb

import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.restapi.tmdb.models.discover.DiscoverResponse
import com.github.tehras.restapi.tmdb.models.genres.GenresResponse
import com.github.tehras.restapi.tmdb.models.moviedetails.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Calendar

/**
 * @author tkoshkin created on 8/26/18
 */
@ApplicationScope
interface TmdbService {
    @GET("/3/discover/movie?api_key=$API_KEY")
    fun discover(
        @Query(value = "primary_release_date.gte") releaseDateGte: String = today(),
        @Query(value = "primary_release_date.lte") releaseDateLte: String = releaseDateGte,
        @Query(value = "sort_by") sortBy: SortBy = SortBy.POPULARITY_DESC
    ): Single<DiscoverResponse>

    @GET("/3/genre/movie/list?api_key=$API_KEY")
    fun genres(): Single<GenresResponse>

    @GET("/3/movie/{movie_id}?api_key=$API_KEY")
    fun movieDetails(@Path("movie_id") id: Long): Single<MovieDetails>
}

private fun today(): String {
    val c = Calendar.getInstance()
    return "${c.get(Calendar.YEAR)}-${c.get(Calendar.MONTH)}-${c.get(Calendar.DAY_OF_MONTH)}"
}