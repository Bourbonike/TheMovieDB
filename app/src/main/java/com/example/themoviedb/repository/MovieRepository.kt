package com.example.themoviedb.repository

import com.example.themoviedb.network.network.models.*
import com.example.themoviedb.network.network.services.MovieService

class MovieRepository(
    private val movieService: MovieService,
) {
    suspend fun getMoviesPopular(): MoviePage = movieService.getMoviesPopular()

    suspend fun getMoviesNowPlaying(): MoviePage = movieService.getMoviesNowPlaying()

    suspend fun getMoviesTopPlaying(): MoviePage = movieService.getMoviesTopPlaying()

    suspend fun getMovieDetails(movieId: Int): MovieDetailsModel =
        movieService.getMovieDetails(movieId)

    suspend fun getActors(movieId: Int): ActorsPage = movieService.getActors(movieId)

}