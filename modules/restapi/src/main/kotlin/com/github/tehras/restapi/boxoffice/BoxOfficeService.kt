package com.github.tehras.restapi.boxoffice

import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.restapi.API_KEY
import com.github.tehras.models.tmdb.models.boxoffice.BoxOffice
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

@ApplicationScope
interface BoxOfficeService {
    @GET("/3/movie/now_playing?api_key=$API_KEY")
    fun nowPlaying(@Query("region") region: String = Locale.US.country): Single<BoxOffice>
}