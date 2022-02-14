package com.example.mvvmmovieapp.repository

import androidx.lifecycle.viewModelScope
import com.example.mvvmmovieapp.database.MovieDao
import com.example.mvvmmovieapp.network.MovieItemList
import com.example.mvvmmovieapp.network.MovieList
import kotlinx.coroutines.launch

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun insertFav(favMovieList: MovieList) {
        movieDao.insert(favMovieList)

    }

    suspend fun deleteFav(deleteMovieList: MovieList) {
        movieDao.delete(deleteMovieList)
    }

    suspend fun getFavMovieList(): MovieItemList {
        return MovieItemList(movieDao.getAllMovieList())
    }
}
