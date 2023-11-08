package com.example.themoviedp.network.network.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("backdropPath")
    val backdropPath: String,
    @SerializedName("poster_path")
    val posterPath: String,
)
