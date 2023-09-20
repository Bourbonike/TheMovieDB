package com.example.themoviedp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.themoviedp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button1.setOnClickListener {

            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", 1008042)
            startActivity(intent)

        }
        binding.button2.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", 615656)
            startActivity(intent)

        }
    }

}