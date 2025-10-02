package com.mcshr.moviebrowser.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcshr.moviebrowser.data.local.database.entities.FavoriteMovieDbModel

@Dao
interface FavoriteMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(movie: FavoriteMovieDbModel)

    @Delete
    suspend fun removeFromFavorites(movie: FavoriteMovieDbModel)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovieDbModel>>

    @Query("SELECT id FROM favorite_movies")
    suspend fun getAllIdsFavoriteMovies(): List<Int>
}