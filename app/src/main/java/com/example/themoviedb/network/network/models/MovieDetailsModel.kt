package com.example.themoviedb.network.network.models

import com.google.gson.annotations.SerializedName

data class GenresModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)

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
    @SerializedName("genres")
    val genres: List<GenresModel>
)
