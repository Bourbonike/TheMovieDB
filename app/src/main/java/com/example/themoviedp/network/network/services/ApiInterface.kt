package com.example.themoviedp.network.network.services

import com.example.themoviedp.network.network.models.MovieDetailsModel
import retrofit2.http.GET
import com.example.themoviedp.network.network.models.MoviePage
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiInterface {
    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") sort: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmM2M0NTBkNWQwOTA0MjI4Y2M3ZmRjN2NlYmE5YmU2MCIsInN1YiI6IjY0OWRkMTlmYzA3MmEyMDEwZGY1NDRiOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PcxFUyP5guH1-eUoxKzr8Pm9TAzmT6X-pEI2Cm2OM6M",
    ): MovieDetailsModel

    @GET("3/movie/popular")
    suspend fun getMoviesPopular(
        @Header("Authorization") sort: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmM2M0NTBkNWQwOTA0MjI4Y2M3ZmRjN2NlYmE5YmU2MCIsInN1YiI6IjY0OWRkMTlmYzA3MmEyMDEwZGY1NDRiOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PcxFUyP5guH1-eUoxKzr8Pm9TAzmT6X-pEI2Cm2OM6M"
    ): MoviePage

}
