package com.example.themoviedp.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.themoviedp.network.network.services.ApiInterface

import com.example.themoviedp.network.network.models.MovieListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class MoviesPopularUi(
    val movieList: List<MovieListModel>,
)

class MainViewModel : ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val popularsApi: ApiInterface = retrofit.create(ApiInterface::class.java)

     val popularsState = MutableStateFlow(
        MoviesPopularUi(movieList = listOf())
    )

    //ыфва

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("TestLog", "кидаем реквест")
                val moviePage = popularsApi.getMoviesPopular()
                popularsState.value = MoviesPopularUi(movieList = moviePage.results)
                Log.d("TestLog", "получили фильмы ${moviePage.results}")

            } catch (
                e: Exception
            ) {
                Log.d("TestLog", "ошибка: $e")

            }


        }

    }


}