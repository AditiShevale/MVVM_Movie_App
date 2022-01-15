package com.example.mvvmmovieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmmovieapp.network.*
import kotlinx.coroutines.launch

class MovieViewModel() : ViewModel() {
    private val _status = MutableLiveData<MovieItemList>()
    val status: LiveData<MovieItemList> = _status

    private val _poster_status = MutableLiveData<MovieBackDropList>()
    val poster_status: LiveData<MovieBackDropList> = _poster_status

    fun sharedPref(data: String?) {

        when (data) {
            "mostPopular" -> getMovieList()
            "comingSoon" -> getComingSoonList()
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

}