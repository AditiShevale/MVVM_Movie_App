package com.example.mvvmmovieapp

import android.app.Application
import com.example.mvvmmovieapp.database.MovieRoomDatabase
import com.example.mvvmmovieapp.repository.MovieRepository

class MovieApplication : Application() {
    val database: MovieRoomDatabase by lazy {
        MovieRoomDatabase.getDatabase(this)
    }
    val repository: MovieRepository by lazy {
        MovieRepository(database.movieDao())
    }
}