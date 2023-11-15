package com.example.themoviedb.main.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.BuildConfig
import com.example.themoviedb.databinding.ItemMovieLayoutBinding
import com.example.themoviedb.network.network.models.MovieListModel

class MovieAdapter(
    private val onItemClicked: (MovieListModel) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var movieList = listOf<MovieListModel>()

    fun setMovieList(movieList: List<MovieListModel>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(BuildConfig.MOVIE_DB_BASE_IMAGES_URL + movieList[position].backdropPath)
            .into(holder.binding.movieImage)
        holder.binding.movieName.text = movieList[position].title
        holder.binding.root.setOnClickListener {
            onItemClicked(movieList[position])
        }
    }

    override fun getItemCount(): Int = movieList.size

    class ViewHolder(val binding: ItemMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}