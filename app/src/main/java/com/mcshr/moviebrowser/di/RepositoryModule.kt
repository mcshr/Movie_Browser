package com.mcshr.moviebrowser.di

import com.mcshr.moviebrowser.data.MoviesRepositoryImpl
import com.mcshr.moviebrowser.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindMovieRepository(
        impl: MoviesRepositoryImpl
    ): MoviesRepository
}