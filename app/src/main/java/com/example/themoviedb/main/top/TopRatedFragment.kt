package com.example.themoviedb.main.top

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.databinding.FragmentTopRatedBinding
import com.example.themoviedb.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedFragment : Fragment() {
    private lateinit var binding: FragmentTopRatedBinding
    private val viewModel: TopViewModel by viewModels()

    private val adapter = MovieTopAdapter(onItemClicked = { movieListModel ->
        val intent = DetailsActivity.createIntent(
            context = requireContext(),
            movieId = movieListModel.id
        )
        startActivity(intent)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopRatedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewMain = binding.rvMovies
        recyclerViewMain.layoutManager = GridLayoutManager(context, 2)
        recyclerViewMain.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularsState.collect { popularState ->
                    adapter.setMovieTopList(popularState.movieTopList)
                    binding.progress.visibility = if (popularState.isLoading) {
                        View.VISIBLE
                    } else {
                        View.INVISIBLE
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.errorFlow.collect { exception ->
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder
                    .setMessage(getString(R.string.request_failed_message))
                    .setTitle(getString(R.string.request_failed_title))
                    .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }
}