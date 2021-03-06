package com.example.mvvmmovieapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://imdb-api.com/en/API/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService {

    @GET("MostPopularMovies/k_b4eqgb92")
    suspend fun getMPopularMovies(): MovieItemList

    @GET("ComingSoon/k_b4eqgb92")
    suspend fun getComingSoonMovies(): MovieItemList

    @GET("Posters/k_b4eqgb92/{id}")
    suspend fun getPoster(@Path("id") id: String): MovieBackDropList

    @GET("Title/k_b4eqgb92/tt1375666")
    suspend fun getDetailListTitle(): MovieFullData
}

object MoviesApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}