package edu.iu.alex.moviesearch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    fun searchMovie(
        @Query("t") title: String,
        @Query("apikey") apiKey: String
    ): Call<MovieResponse>
}