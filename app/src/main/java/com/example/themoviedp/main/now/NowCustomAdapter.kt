package com.example.themoviedp.main


import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.themoviedp.databinding.MovieLayout1Binding
import com.example.themoviedp.network.network.models.MovieListModel


class MovieNowAdapter(
    private val onItemClicked: (MovieListModel) -> Unit
) : RecyclerView.Adapter<MovieNowAdapter.ViewHolder>() {
    private var movieNowList = listOf<MovieListModel>()

    fun setMovieNowList(movieNowList: List<MovieListModel>) {
        this.movieNowList = movieNowList
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
            .load("https://image.tmdb.org/t/p/w500/" + movieNowList[position].backdropPath)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("TEST_LOG", "FAILED $e")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("TEST_LOG", "LOADED")
                    return false
                }

            })
            .into(holder.binding.movieImage)
        holder.binding.movieName.text = movieNowList[position].title
        holder.binding.root.setOnClickListener {
            onItemClicked(movieNowList[position])
        }
    }

    override fun getItemCount(): Int {
        return movieNowList.size
    }
}