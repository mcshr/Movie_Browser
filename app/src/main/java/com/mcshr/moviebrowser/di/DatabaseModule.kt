package com.mcshr.moviebrowser.di

import android.content.Context
import androidx.room.Room
import com.mcshr.moviebrowser.data.local.database.FavoriteMoviesDao
import com.mcshr.moviebrowser.data.local.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            MoviesDatabase.DB_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavMoviesDao(
        db: MoviesDatabase
    ): FavoriteMoviesDao{
        return db.favouriteMoviesDao()
    }
}