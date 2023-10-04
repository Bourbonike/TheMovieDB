package com.example.themoviedp.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedp.databinding.ActorsLayoutBinding
import com.example.themoviedp.network.network.models.ActorsListModel


class ActorsAdapter() : RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {
    private var actorsList = listOf<ActorsListModel>()
    fun setActorsList(actorsList: List<ActorsListModel>) {
        this.actorsList = actorsList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ActorsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ActorsLayoutBinding.inflate(
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
            .load("https://image.tmdb.org/t/p/w500/" + actorsList[position].profilePath)
            .into(holder.binding.actorImage)
        holder.binding.actorName.text = actorsList[position].name
    }

    override fun getItemCount(): Int {
        return actorsList.size
    }
}