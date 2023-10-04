package com.example.themoviedp.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedp.databinding.ActivityMainBinding
import com.example.themoviedp.details.DetailsActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = MovieAdapter(onItemClicked = { movieListModel ->
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra("id", movieListModel.id)
        startActivity(intent)
    })

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //подключаем рецйкл
        val recyclerViewMain = binding.rvMovies
        recyclerViewMain.layoutManager = GridLayoutManager(this,2)


        recyclerViewMain.adapter = adapter


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularsState.collect { popularState ->
                    adapter.setMovieList(popularState.movieList)
                    binding
//                    binding.button1.setOnClickListener {
//                        val movie1 = popularState.movieList.getOrNull(0)
//                        if (movie1 != null) {
//                            val intent = Intent(this@MainActivity, DetailsActivity::class.java)
//                            intent.putExtra("id", movie1.id)
//                            startActivity(intent)
//                        }
//
//                    }
                }
            }
        }
    }


}

