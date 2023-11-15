package com.example.themoviedb.main.now

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.network.network.models.MovieListModel
import com.example.themoviedb.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val errorFlow = MutableSharedFlow<Throwable>()

    val nowState = MutableStateFlow(MoviesListUIState(movieNowList = listOf(), false))

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                nowState.value = MoviesListUIState(movieNowList = listOf(), isLoading = true)
                delay(1000)
                movieRepository.getMoviesNowPlaying()
            }.onSuccess { moviePage ->
                nowState.value =
                    MoviesListUIState(movieNowList = moviePage.results, isLoading = false)
            }.onFailure { error ->
                errorFlow.emit(error)
                nowState.value = MoviesListUIState(movieNowList = listOf(), isLoading = false)
            }
        }
    }

    data class MoviesListUIState(
        val movieNowList: List<MovieListModel>,
        val isLoading: Boolean,
    )
}
