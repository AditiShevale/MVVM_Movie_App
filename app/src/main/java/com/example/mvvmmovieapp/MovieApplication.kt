package com.example.mvvmmovieapp

import android.app.Application
import com.example.mvvmmovieapp.database.MovieRoomDatabase

class MovieApplication: Application() {
    val database: MovieRoomDatabase by  lazy {
        MovieRoomDatabase.getDatabase(this)
    }
}