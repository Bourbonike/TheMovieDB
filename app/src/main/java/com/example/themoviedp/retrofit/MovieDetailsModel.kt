package com.example.themoviedp.retrofit

import com.google.gson.annotations.SerializedName

data class MovieDetailsModel(
    val id: Int,
    val overview: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
)