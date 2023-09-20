package com.example.themoviedp

import androidx.lifecycle.ViewModel
import com.example.themoviedp.retrofit.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class MovieDetailsUi(
    val id: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
)


class DetailsViewModel : ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val movieApi: ApiInterface = retrofit.create(ApiInterface::class.java)


    val movieState = MutableStateFlow(
        MovieDetailsUi(
            id = 0,
            title = "",
            overview = "",
            voteAverage = 0.0
        )
    )

    fun setMovieDetails(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val movie = movieApi.getMovieDetails(id)
            movieState.value = MovieDetailsUi(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                voteAverage = movie.voteAverage
            )
        }
    }

    fun changeMovie() {
        movieState.value =
            MovieDetailsUi(id = 1, title = "title", overview = "Вячеславович", voteAverage = 10.0)
    }


}