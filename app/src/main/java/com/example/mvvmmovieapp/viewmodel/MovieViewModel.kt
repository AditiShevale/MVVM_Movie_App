package com.example.mvvmmovieapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.database.MovieDao
import com.example.mvvmmovieapp.network.*
import com.example.mvvmmovieapp.repository.MovieRepository
import com.example.mvvmmovieapp.workers.movieWorker
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class MovieViewModel(private val repository: MovieRepository, application: Application) :
    ViewModel() {

    private val _status = MutableLiveData<MovieItemList>()
    val status: LiveData<MovieItemList> = _status

    private val _poster_status = MutableLiveData<MovieBackDropList>()
    val poster_status: LiveData<MovieBackDropList> = _poster_status

    private val workManager = WorkManager.getInstance(application)

    internal fun movieReminder() {

        val movieNotification = OneTimeWorkRequestBuilder<movieWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        workManager.beginUniqueWork("movies", ExistingWorkPolicy.REPLACE, movieNotification)
            .enqueue()
    }

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
            _status.value = repository.getfavMovieList()
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
            repository.InsertFav(favMovieList)
        }
    }

    fun DeleteFav(deleteMovieList: MovieList) {
        viewModelScope.launch {
            repository.DeleteFav(deleteMovieList)
        }
    }
}

class MovieViewModelFactory(
    private val repository: MovieRepository,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}