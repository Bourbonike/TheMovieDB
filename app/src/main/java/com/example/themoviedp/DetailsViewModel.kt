package com.example.themoviedp

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

data class MovieDetailsUiState(
    val title: String,
    val description: String,
)

class DetailsViewModel : ViewModel() {

    val movieState = MutableStateFlow(
        MovieDetailsUiState(
            title = "Cinema",
            description = "AAA"
        )
    )

    init {
        Log.e("AAA", "VM created")
    }

    fun changeMovie() {
        movieState.value = MovieDetailsUiState(title = "Игорь", description = "Вячеславович")
    }

    override fun onCleared() {
        Log.e("AAA", "VM cleared")
        super.onCleared()
    }
}