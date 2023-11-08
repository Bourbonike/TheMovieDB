package com.example.themoviedp.repozitory

import com.example.themoviedp.network.network.models.*
import com.example.themoviedp.network.network.services.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(
    private val movieService: ApiInterface,
) {
    suspend fun getMoviesPopular(): MoviePage = withContext(Dispatchers.IO) {
        movieService.getMoviesPopular()
    }

    suspend fun getMoviesNowPlaying(): MoviePage =
        movieService.getMoviesNowPlaying()

    suspend fun getMoviesTopPlaying(): MoviePage =
        movieService.getMoviesTopPlaying()

    suspend fun getMovieDetails(movieId: Int): MovieDetailsModel =
        movieService.getMovieDetails(movieId)

    suspend fun getActors(movieId: Int): ActorsPage =
        movieService.getActors(movieId)

}