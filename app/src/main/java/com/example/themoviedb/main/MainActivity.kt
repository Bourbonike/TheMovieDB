package com.example.themoviedb.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.themoviedb.R
import com.example.themoviedb.main.now.NowWatchFragment
import com.example.themoviedb.main.popular.PopularFragment
import com.example.themoviedb.main.top.TopRatedFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val fragments = listOf(PopularFragment(), NowWatchFragment(), TopRatedFragment())
        val adapter = ViewPagerAdapter(fragments, this)
        val tabNames: Array<String> = arrayOf(
            getString(R.string.popular_tab_name),
            getString(R.string.now_watching_tab_name),
            getString(R.string.top_rated_tab_name),
        )
        viewPager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }
}

