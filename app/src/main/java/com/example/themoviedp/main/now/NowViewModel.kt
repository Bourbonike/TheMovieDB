package com.example.themoviedp.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedp.network.network.models.MovieListModel
import com.example.themoviedp.repozitory.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoviesFragment2Ui(
    val movieNowList: List<MovieListModel>,
    val isLoading: Boolean,
)

@HiltViewModel
class NowViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val errorFlow = MutableSharedFlow<Throwable>()

    val nowState = MutableStateFlow(
        MoviesFragment2Ui(movieNowList = listOf(), false)
    )

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                nowState.value = MoviesFragment2Ui(movieNowList = listOf(), isLoading = true)
                delay(2000)
                movieRepository.getMoviesNowPlaying()
            }.onSuccess { moviePage ->
                nowState.value =
                    MoviesFragment2Ui(movieNowList = moviePage.results, isLoading = false)
            }.onFailure { error ->
                errorFlow.emit(error)
                nowState.value = MoviesFragment2Ui(movieNowList = listOf(), isLoading = false)
            }
        }
    }
}
