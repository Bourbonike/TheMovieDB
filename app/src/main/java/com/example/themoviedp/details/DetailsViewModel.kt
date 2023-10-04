package com.example.themoviedp.details


import androidx.lifecycle.ViewModel
import com.example.themoviedp.network.network.services.ApiInterface
import com.example.themoviedp.network.network.models.ActorsListModel
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

data class ActorsPopularUi(
    val actorsList: List<ActorsListModel>,
)

class DetailsViewModel : ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val movieApi: ApiInterface = retrofit.create(ApiInterface::class.java)


    val actorsState = MutableStateFlow(
        ActorsPopularUi(actorsList = listOf())
    )

    val movieState = MutableStateFlow(
        MovieDetailsUi(
            id = 0,
            title = "",
            overview = "",
            voteAverage = 0.0,
        )
    )

    fun setMovieDetails(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val movie = movieApi.getMovieDetails(id)
            val actorsPage = movieApi.getActors(id)
            actorsState.value = ActorsPopularUi(actorsList = actorsPage.results)
            movieState.value = MovieDetailsUi(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                voteAverage = movie.voteAverage,
            )

        }
    }
}

//    fun changeMovie() {
//        movieState.value =
//            MovieDetailsUi(id = 1, title = "title", overview = "Вячеславович", voteAverage = 10.0)
//    }
//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//
//                Log.d("TestLogActors", "кидаем реквест")
//
//                Log.d("TestLogActors", "id=${615656}")
//
//                Log.d(
//                    "TestLogActors", "получили фильмы ${actorsPage.results}"
//                )
//
//            } catch (
//                e: Exception
//            ) {
//                Log.d("TestLogActors", "ошибка: $e")
//
//            }
//
//
//        }
//
//    }
//
//
//}