package com.example.themoviedb.main.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.BuildConfig
import com.example.themoviedb.databinding.ItemMovieLayoutBinding

import com.example.themoviedb.network.network.models.MovieListModel

class MovieTopAdapter(
    private val onItemClicked: (MovieListModel) -> Unit
) : RecyclerView.Adapter<MovieTopAdapter.ViewHolder>() {
    private var movieTopList = listOf<MovieListModel>()

    fun setMovieTopList(movieTopList: List<MovieListModel>) {
        this.movieTopList = movieTopList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(BuildConfig.MOVIE_DB_BASE_IMAGES_URL + movieTopList[position].backdropPath)
            .into(holder.binding.movieImage)
        holder.binding.movieName.text = movieTopList[position].title
        holder.binding.root.setOnClickListener {
            onItemClicked(movieTopList[position])
        }
    }

    override fun getItemCount(): Int = movieTopList.size

    class ViewHolder(val binding: ItemMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}