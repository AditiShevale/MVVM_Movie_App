package com.example.mvvmmovieapp.network

import com.squareup.moshi.Json

data class MovieList(
    val id: String,
    val title: String
)

data class MovieItemList(
    val items : List<MovieList>
)