package com.example.themoviedb.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.BuildConfig
import com.example.themoviedb.R
import com.example.themoviedb.databinding.ItemActorLayoutBinding
import com.example.themoviedb.network.network.models.ActorsListModel

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {

    private var actorsList = listOf<ActorsListModel>()

    fun setActorsList(actorsList: List<ActorsListModel>) {
        this.actorsList = actorsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemActorLayoutBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(BuildConfig.MOVIE_DB_BASE_IMAGES_URL + actorsList[position].profilePath)
            .error(R.drawable.no_image)
            .into(holder.binding.actorImage)

        holder.binding.actorName.text = actorsList[position].name
    }

    override fun getItemCount(): Int = actorsList.size

    class ViewHolder(val binding: ItemActorLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}