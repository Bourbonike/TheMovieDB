package com.example.themoviedp.network.network.models

import com.google.gson.annotations.SerializedName

data class ActorsListModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String,
)

data class ActorsPage(
    @SerializedName("cast")
    val results: List<ActorsListModel>
)