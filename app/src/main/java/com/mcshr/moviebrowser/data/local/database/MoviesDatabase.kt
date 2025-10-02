package com.mcshr.moviebrowser.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcshr.moviebrowser.data.local.database.entities.FavoriteMovieDbModel

@Database(
    version = 1,
    entities = [
        FavoriteMovieDbModel::class
    ],
)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun favouriteMoviesDao(): FavoriteMoviesDao

    companion object{
        const val DB_NAME = "MoviesDATABASE"
    }
}