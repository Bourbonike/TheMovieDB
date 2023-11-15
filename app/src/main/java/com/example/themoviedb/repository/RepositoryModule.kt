package com.example.themoviedb.repository

import com.example.themoviedb.network.network.services.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(apiInterface: MovieService): MovieRepository {
        return MovieRepository(apiInterface)
    }
}