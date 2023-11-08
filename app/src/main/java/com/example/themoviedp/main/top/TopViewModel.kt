package com.example.themoviedp.main.top


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedp.network.network.models.MovieListModel
import com.example.themoviedp.repozitory.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

data class TopUi(
    val movieTopList: List<MovieListModel>,
    val isLoading: Boolean,
)

@HiltViewModel
class TopViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val errorFlow = MutableSharedFlow<Throwable>()

    val popularsState = MutableStateFlow(
        TopUi(movieTopList = listOf(), false)
    )

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                popularsState.value = TopUi(movieTopList = listOf(), isLoading = true)
                delay(2000)
                movieRepository.getMoviesTopPlaying()
            }.onSuccess { moviePage ->
                popularsState.value = TopUi(movieTopList = moviePage.results, isLoading = false)
            }.onFailure { error ->
                errorFlow.emit(error)
                popularsState.value = TopUi(movieTopList = listOf(), isLoading = false)
            }
        }
    }
}
