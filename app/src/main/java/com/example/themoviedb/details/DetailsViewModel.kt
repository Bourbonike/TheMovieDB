package com.example.themoviedb.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.network.network.models.*
import com.example.themoviedb.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val errorFlow = MutableSharedFlow<Throwable>()

    val movieState = MutableStateFlow(
        MovieDetailsUIState(
            detailsModel = null,
            actorsList = listOf(),
            false,
        )
    )

    fun setMovieDetails(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                movieState.value = MovieDetailsUIState(
                    detailsModel = null,
                    actorsList = listOf(),
                    isLoading = true,
                )
                delay(1000)
                val movieDeferred = async { movieRepository.getMovieDetails(id) }
                val actorsPageDeferred = async { movieRepository.getActors(id) }
                val movie = movieDeferred.await()
                val actorsPage = actorsPageDeferred.await()
                MovieDetailsUIState(detailsModel = movie, actorsList = actorsPage.results, false)
            }.onSuccess { movieDetailsUi ->
                movieState.value = movieDetailsUi
            }.onFailure { error ->
                errorFlow.emit(error)
            }
        }
    }

    data class MovieDetailsUIState(
        val detailsModel: MovieDetailsModel?,
        val actorsList: List<ActorsListModel>,
        val isLoading: Boolean,
    )
}
