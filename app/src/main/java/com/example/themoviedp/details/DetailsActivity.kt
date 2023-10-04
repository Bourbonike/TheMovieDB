package com.example.themoviedp.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedp.databinding.ActivityDetailsScreenBinding
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsScreenBinding

    private val viewModel: DetailsViewModel by viewModels()
    private val adapter = ActorsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("AAA", "Activity created")

        val recyclerViewActors = binding.rvActors
        recyclerViewActors.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewActors.adapter = adapter

        viewModel.setMovieDetails(
            id = intent.getIntExtra("id", 0),
        )


//        binding.button.setOnClickListener { viewModel.changeMovie() }
//
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actorsState.collect { actorsState ->
                    adapter.setActorsList(actorsState.actorsList)
                    binding
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect { movieState ->
                    binding.toolbar.title = movieState.title
                    binding.synopsis.text = movieState.overview
                    binding.textView3.text = movieState.voteAverage.toString()
                }
            }
        }
    }
}
