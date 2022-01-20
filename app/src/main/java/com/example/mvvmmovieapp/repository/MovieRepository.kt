package com.example.mvvmmovieapp.repository

import androidx.lifecycle.viewModelScope
import com.example.mvvmmovieapp.database.MovieDao
import com.example.mvvmmovieapp.network.MovieItemList
import com.example.mvvmmovieapp.network.MovieList
import kotlinx.coroutines.launch

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun InsertFav(favMovieList: MovieList) {
        movieDao.insert(favMovieList)

    }

    suspend fun DeleteFav(deleteMovieList: MovieList) {
        movieDao.delete(deleteMovieList)
    }

    suspend fun getfavMovieList(): MovieItemList {
        return MovieItemList(movieDao.getAllMovieList())
    }
}
