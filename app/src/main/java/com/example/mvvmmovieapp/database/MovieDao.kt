package com.example.mvvmmovieapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.mvvmmovieapp.network.MovieList
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * from movie")
 suspend fun getAllMovieList():List<MovieList>

    @Insert(onConflict = REPLACE)
    suspend fun insert(item: MovieList)

    @Delete
    suspend fun delete(item: MovieList)
}