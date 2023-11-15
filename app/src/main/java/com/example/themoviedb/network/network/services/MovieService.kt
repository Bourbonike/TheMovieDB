package com.example.themoviedb.network.network.services

import com.example.themoviedb.network.network.models.*
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): MovieDetailsModel

    @GET("3/movie/popular")
    suspend fun getMoviesPopular(): MoviePage

    @GET("3/movie/now_playing")
    suspend fun getMoviesNowPlaying(): MoviePage

    @GET("3/movie/top_rated")
    suspend fun getMoviesTopPlaying(): MoviePage

    @GET("3/movie/{movie_id}/credits")
    suspend fun getActors(
        @Path("movie_id") movieId: Int,
    ): ActorsPage
}
