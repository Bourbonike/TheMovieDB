package com.example.themoviedb.main.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.network.network.models.MovieListModel
import com.example.themoviedb.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val errorFlow = MutableSharedFlow<Throwable>()

    val popularsState = MutableStateFlow(
        MoviesListUIState(movieTopList = listOf(), false)
    )

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                popularsState.value = MoviesListUIState(movieTopList = listOf(), isLoading = true)
                delay(1000)
                movieRepository.getMoviesTopPlaying()
            }.onSuccess { moviePage ->
                popularsState.value = MoviesListUIState(movieTopList = moviePage.results, isLoading = false)
            }.onFailure { error ->
                errorFlow.emit(error)
                popularsState.value = MoviesListUIState(movieTopList = listOf(), isLoading = false)
            }
        }
    }

    data class MoviesListUIState(
        val movieTopList: List<MovieListModel>,
        val isLoading: Boolean,
    )
}
