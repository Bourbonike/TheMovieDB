package com.example.themoviedp.main.popular

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

data class PopularUi(
    val movieList: List<MovieListModel>,
    val isLoading: Boolean,
)

@HiltViewModel
class PopularViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val errorFlow = MutableSharedFlow<Throwable>()

    val popularsState = MutableStateFlow(
        PopularUi(movieList = listOf(), false)
    )

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                popularsState.value = PopularUi(movieList = listOf(), isLoading = true)
                delay(2000)
                movieRepository.getMoviesPopular()
            }.onSuccess { moviePage ->
                popularsState.value = PopularUi(movieList = moviePage.results, isLoading = false)
            }.onFailure { error ->
                errorFlow.emit(error)
                popularsState.value = PopularUi(movieList = listOf(), isLoading = false)
            }
        }
    }
}