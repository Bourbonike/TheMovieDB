package com.example.themoviedb.main.now

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.themoviedb.BuildConfig
import com.example.themoviedb.databinding.ItemMovieLayoutBinding
import com.example.themoviedb.network.network.models.MovieListModel


class MovieNowAdapter(
    private val onItemClicked: (MovieListModel) -> Unit
) : RecyclerView.Adapter<MovieNowAdapter.ViewHolder>() {

    private var movieNowList = listOf<MovieListModel>()

    fun setMovieNowList(movieNowList: List<MovieListModel>) {
        this.movieNowList = movieNowList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMovieLayoutBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(BuildConfig.MOVIE_DB_BASE_IMAGES_URL + movieNowList[position].backdropPath)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(holder.binding.movieImage)
        holder.binding.movieName.text = movieNowList[position].title
        holder.binding.root.setOnClickListener {
            onItemClicked(movieNowList[position])
        }
    }

    override fun getItemCount(): Int = movieNowList.size

    class ViewHolder(val binding: ItemMovieLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}