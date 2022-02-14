package com.example.mvvmmovieapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mvvmmovieapp.network.*
import com.example.mvvmmovieapp.repository.MovieRepository
import com.example.mvvmmovieapp.workers.movieWorker
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class MovieViewModel(private val repository: MovieRepository, application: Application) :
    ViewModel() {

    private val _status = MutableLiveData<MovieItemList>()
    val status: LiveData<MovieItemList> = _status

    private val _poster_status = MutableLiveData<MovieBackDropList>()
    val poster_status: LiveData<MovieBackDropList> = _poster_status

    private val _detailData_status = MutableLiveData<MovieFullData>()
    val detailData_status: LiveData<MovieFullData> = _detailData_status

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
            "favorite" -> getFavMovieList()
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

    fun getFavMovieList() {
        viewModelScope.launch {
            _status.value = repository.getFavMovieList()
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

    fun insertFav(favMovieList: MovieList) {
        viewModelScope.launch {
            repository.insertFav(favMovieList)
        }
    }

    fun deleteFav(deleteMovieList: MovieList) {
        viewModelScope.launch {
            repository.deleteFav(deleteMovieList)
        }
    }

    fun detailData() {
        viewModelScope.launch {
            try {
                val movieDetailResult = MoviesApi.retrofitService.getDetailListTitle()
//                Log.d("qxqxqx", movieDetailResult.toString())
                _detailData_status.value = movieDetailResult

            } catch (e: java.lang.Exception) {
                Log.d("qxqxqx", e.message.toString())
            }


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