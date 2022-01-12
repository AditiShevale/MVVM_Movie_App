package com.example.mvvmmovieapp.network

import android.media.Image
import android.widget.ImageView
import com.squareup.moshi.Json

data class MovieList(
    val id: String,
    val title: String,
    val year:String,
    val image:String
)

data class MovieItemList(
    val items : List<MovieList>
)