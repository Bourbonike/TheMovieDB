package com.example.themoviedp.main.popular

import android.app.AlertDialog
import android.content.Intent
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
import com.example.themoviedp.databinding.PopularBinding
import com.example.themoviedp.details.DetailsActivity
import com.example.themoviedp.main.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class Popular : Fragment() {
    private lateinit var binding: PopularBinding
    private val viewModel: PopularViewModel by viewModels()

    private val adapter = MovieAdapter(onItemClicked = { movieListModel ->
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("id", movieListModel.id)
        startActivity(intent)
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PopularBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //подключаем рецйкл
        val recyclerViewMain = binding.rvMovies
        recyclerViewMain.layoutManager = GridLayoutManager(context, 2)
        recyclerViewMain.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularsState.collect { popularState ->
                    adapter.setMovieList(popularState.movieList)
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