package com.example.themoviedp.details


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedp.network.network.models.ActorsListModel
import com.example.themoviedp.network.network.models.MovieDetailsModel
import com.example.themoviedp.repozitory.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MovieDetailsUi(
    val detailsModel: MovieDetailsModel?,
    val actorsList: List<ActorsListModel>,
    val isLoading: Boolean,
)


@HiltViewModel
class DetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val errorFlow = MutableSharedFlow<Throwable>()

    val movieState = MutableStateFlow(
        MovieDetailsUi(
            detailsModel = null,
            actorsList = listOf(),
            false
        )
    )

    fun setMovieDetails(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                movieState.value = MovieDetailsUi(
                    detailsModel = null,
                    actorsList = listOf(),
                    isLoading = true
                )
                delay(2000)
                val movieDeferred = async { movieRepository.getMovieDetails(id) }
                val actorsPageDeferred = async { movieRepository.getActors(id) }
                val movie = movieDeferred.await()
                val actorsPage = actorsPageDeferred.await()
                MovieDetailsUi(detailsModel = movie, actorsList = actorsPage.results, false)
            }.onSuccess { movieDetailsUi ->
                movieState.value =
                    movieDetailsUi
            }.onFailure { error ->
                errorFlow.emit(error)
            }
        }
    }
}
