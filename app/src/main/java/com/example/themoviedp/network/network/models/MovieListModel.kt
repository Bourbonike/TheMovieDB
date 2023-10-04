package com.example.themoviedp.network.network.models

import com.google.gson.annotations.SerializedName

data class MovieListModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,

    val backdrop_path:String,
)

data class MoviePage(
    @SerializedName("results")
    val results: List<MovieListModel>
)