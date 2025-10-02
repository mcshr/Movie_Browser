package com.mcshr.moviebrowser.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieDbModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "full_description") val fullDescription: String,
    @ColumnInfo(name = "poster_url") val posterUrl: String,
    @ColumnInfo(name = "release_date") val releaseDate: String
)
