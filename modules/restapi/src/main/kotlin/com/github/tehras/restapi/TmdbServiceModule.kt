package com.github.tehras.restapi

import com.github.tehras.dagger.scopes.ApplicationScope
import com.github.tehras.restapi.boxoffice.BoxOfficeService
import com.github.tehras.restapi.discover.TmdbService
import com.github.tehras.restapi.movies.MoviesService
import com.github.tehras.restapi.person.PersonService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author tkoshkin created on 8/26/18
 */
@Module
class TmdbServiceModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @ApplicationScope
        fun provideTmdbService(retrofit: Retrofit): TmdbService = retrofit.create(TmdbService::class.java)

        @Provides
        @JvmStatic
        @ApplicationScope
        fun provideMovieService(retrofit: Retrofit): MoviesService = retrofit.create(MoviesService::class.java)

        @Provides
        @JvmStatic
        @ApplicationScope
        fun provideBoxOfficeService(retrofit: Retrofit): BoxOfficeService = retrofit.create(BoxOfficeService::class.java)

        @Provides
        @JvmStatic
        @ApplicationScope
        fun providePersonService(retrofit: Retrofit): PersonService = retrofit.create(PersonService::class.java)

        @Provides
        @JvmStatic
        @ApplicationScope
        fun provideRetrofit(okHttpClient: OkHttpClient, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory, moshiConverterFactory: MoshiConverterFactory): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .addConverterFactory(moshiConverterFactory)
                    .client(okHttpClient)
                    .build()
        }
    }
}

