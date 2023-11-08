package com.example.themoviedp.main


import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.themoviedp.R
import com.example.themoviedp.main.now.NowWatch
import com.example.themoviedp.main.popular.Popular
import com.example.themoviedp.main.top.TopRated
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val fragments =
            listOf(Popular(), NowWatch(), TopRated()) // Replace with your fragment instances
        val adapter = ViewPagerAdapter(fragments, this)
        val tabNames: Array<String> = arrayOf(
            "Popular",
            "Now Watching",
            "Top Rated",
        )
        viewPager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Set tab text or icon here if needed
            tab.text = tabNames[position]
        }.attach()
    }
}

