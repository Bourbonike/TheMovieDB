package com.example.themoviedp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedp.databinding.MovieLayout1Binding

import com.example.themoviedp.network.network.models.MovieListModel

class MovieAdapter(
    private val onItemClicked: (MovieListModel) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var movieList = listOf<MovieListModel>()
    fun setMovieList(movieList: List<MovieListModel>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: MovieLayout1Binding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieLayout1Binding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500/" + movieList[position].backdrop_path)
            .into(holder.binding.movieImage)
        holder.binding.movieName.text = movieList[position].title
        holder.binding.root.setOnClickListener {
            onItemClicked(movieList[position])
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}