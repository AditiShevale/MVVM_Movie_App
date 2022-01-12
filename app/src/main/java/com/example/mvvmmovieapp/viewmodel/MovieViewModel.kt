package com.example.mvvmmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmmovieapp.network.MovieApiService
import com.example.mvvmmovieapp.network.MovieItemList
import com.example.mvvmmovieapp.network.MovieList
import com.example.mvvmmovieapp.network.MoviesApi
import kotlinx.coroutines.launch

class MovieViewModel() : ViewModel() {
    private val _status = MutableLiveData<MovieItemList>()
    val status: LiveData<MovieItemList> = _status

    init {
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            try {

                val movieItemResult = MoviesApi.retrofitService.getMPopularMovies()
                _status.value = movieItemResult


            } catch (e: Exception) {
                //_status.value = "Failure: ${e.message}"
            }
        }

    }

}