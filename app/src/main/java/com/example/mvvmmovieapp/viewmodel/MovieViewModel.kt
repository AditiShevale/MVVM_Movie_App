package com.example.mvvmmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmmovieapp.network.MovieApiService
import com.example.mvvmmovieapp.network.MovieList
import com.example.mvvmmovieapp.network.MoviesApi
import kotlinx.coroutines.launch

class MovieViewModel() : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    init {
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            try {

                val movieItemResult = MoviesApi.retrofitService.getMPopularMovies()
                _status.value = movieItemResult.items[0].title
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }

    }

}