package com.example.themoviedb.main.popular

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
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val errorFlow = MutableSharedFlow<Throwable>()

    val popularsState = MutableStateFlow(
        MoviesListUIState(movieList = listOf(), false)
    )

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                popularsState.value = MoviesListUIState(movieList = listOf(), isLoading = true)
                delay(1000)
                movieRepository.getMoviesPopular()
            }.onSuccess { moviePage ->
                popularsState.value =
                    MoviesListUIState(movieList = moviePage.results, isLoading = false)
            }.onFailure { error ->
                errorFlow.emit(error)
                popularsState.value = MoviesListUIState(movieList = listOf(), isLoading = false)
            }
        }
    }

    data class MoviesListUIState(
        val movieList: List<MovieListModel>,
        val isLoading: Boolean,
    )
}