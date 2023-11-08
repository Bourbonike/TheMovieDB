package com.example.themoviedp.repozitory

import com.example.themoviedp.network.network.services.ApiInterface
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
    fun provideMovieRepository(apiInterface: ApiInterface): MovieRepository {
        return MovieRepository(apiInterface)
    }
}