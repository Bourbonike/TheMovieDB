package com.example.themoviedb.details

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.themoviedb.BuildConfig
import com.example.themoviedb.R
import com.example.themoviedb.databinding.ActivityDetailsScreenBinding
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

        binding.rvActors.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvActors.adapter = adapter

        viewModel.setMovieDetails(id = intent.getIntExtra(MOVIE_ID_KEY, 0))

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
                        binding.movieRating.text = voteAverage.toString()
                    }
                    movieState.detailsModel?.genres?.let { genres ->
                        binding.genres.text = genres.map { it.name }.joinToString("\n")
                    }
                    Glide.with(this@DetailsActivity)
                        .load(BuildConfig.MOVIE_DB_BASE_IMAGES_URL + viewModel.movieState.value.detailsModel?.posterPath)
                        .into(binding.toolbarImage)
                    binding.textGenres.visibility = if (movieState.isLoading) {
                        View.INVISIBLE
                    } else {
                        View.VISIBLE
                    }
                    binding.star.visibility = if (movieState.isLoading) {
                        View.INVISIBLE
                    } else {
                        View.VISIBLE
                    }
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
                    .setMessage(getString(R.string.request_failed_message))
                    .setTitle(getString(R.string.request_failed_title))
                    .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    companion object {

        private const val MOVIE_ID_KEY = "MOVIE_ID_KEY"

        fun createIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, movieId)
            return intent
        }
    }
}
