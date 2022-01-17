package com.example.mvvmmovieapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.mvvmmovieapp.database.MovieDao
import com.example.mvvmmovieapp.network.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MovieViewModel(private val movieDao: MovieDao) : ViewModel() {
    private val _status = MutableLiveData<MovieItemList>()
    val status: LiveData<MovieItemList> = _status

    private val _poster_status = MutableLiveData<MovieBackDropList>()
    val poster_status: LiveData<MovieBackDropList> = _poster_status

    fun sharedPref(data: String?) {

        when (data) {
            "mostPopular" -> getMovieList()
            "comingSoon" -> getComingSoonList()
            "favorite" -> getfavMovieList()
        }
    }

    fun getMovieList() {
        viewModelScope.launch {
            try {

                val movieItemResult = MoviesApi.retrofitService.getMPopularMovies()
                _status.value = movieItemResult


            } catch (e: Exception) {
                //_status.value = "Failure: ${e.message}"
            }
        }

    }

    fun getComingSoonList() {

        viewModelScope.launch {
            try {

                val movieItemResult = MoviesApi.retrofitService.getComingSoonMovies()
                _status.value = movieItemResult


            } catch (e: Exception) {
                //_status.value = "Failure: ${e.message}"
            }
        }
    }

    fun getfavMovieList() {
        viewModelScope.launch {
            val movieData = MovieItemList(movieDao.getAllMovieList())
            _status.value = movieData
        }
    }

    fun getImageBackDrop(id: String) {
        viewModelScope.launch {
            try {
                val backdropItemResult = MoviesApi.retrofitService.getPoster(id)
                _poster_status.value = backdropItemResult
                Log.d("xgxxx", "success")

            } catch (e: Exception) {
                //_status.value = "Failure: ${e.message}"
                Log.d("xgxxx", e.message.toString())
            }
        }
    }

    fun InsertFav(favMovieList: MovieList) {
        viewModelScope.launch {
            movieDao.insert(favMovieList)
        }
    }

    fun DeleteFav(deleteMovieList: MovieList) {

        viewModelScope.launch {
            movieDao.delete(deleteMovieList)
        }
    }
}

class MovieViewModelFactory(private val movieDao: MovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(movieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}