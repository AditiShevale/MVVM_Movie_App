package com.example.mvvmmovieapp

import coil.Coil.enqueue
import com.example.mvvmmovieapp.network.MovieApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieiesApiServiceTests : BaseTest() {
    private lateinit var service: MovieApiService

    @Before
    fun setup() {
        val url = mockWebServer.url("/")
        service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
            .create(MovieApiService::class.java)
    }

    @Test
    fun api_service() {
        enqueue("movies_photo.json")
        runBlocking {
            val apiResponse = service.getMPopularMovies()
            assertNotNull(apiResponse)
            assertTrue("The list was empty", apiResponse.items.isNotEmpty())
            assertEquals("The IDs did not match", "tt9032400", apiResponse.items[0].id)
        }
    }
}