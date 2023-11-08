package com.example.themoviedp.details

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.themoviedp.databinding.ActivityDetailsScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsScreenBinding

    private val viewModel: DetailsViewModel by viewModels()
    private val adapter = ActorsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageUrl = "https://image.tmdb.org/t/p/w500/"

        Log.e("AAA", "Activity created")

        val recyclerViewActors = binding.rvActors
        recyclerViewActors.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewActors.adapter = adapter

        viewModel.setMovieDetails(
            id = intent.getIntExtra("id", 0),
        )

        Log.d(
            "TestLogDetails",
            "получили фильмы ${viewModel.movieState.value.detailsModel?.posterPath}"
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect { actorsState ->
                    adapter.setActorsList(actorsState.actorsList)
                    binding
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect { movieState ->
                    binding.toolbar.title = movieState.detailsModel?.title
                    movieState.detailsModel?.overview?.let { overview ->
                        binding.synopsis.text = overview
                    }
                    movieState.detailsModel?.voteAverage?.let { voteAverage ->
                        binding.textView3.text = voteAverage.toString()
                    }
                    Glide.with(this@DetailsActivity)
                        .load(imageUrl + viewModel.movieState.value.detailsModel?.posterPath)
                        .into(binding.toolbarImage)
                    binding.progress.visibility = if (movieState.isLoading) {
                        View.VISIBLE
                    } else {
                        View.INVISIBLE
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.errorFlow.collect { exception ->
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@DetailsActivity)
                builder
                    .setMessage("$exception")
                    .setTitle("Oops")
                    .setPositiveButton("OK") { dialog, which ->
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }
}
