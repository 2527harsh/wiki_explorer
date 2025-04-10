package com.example.wikiexplorer.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.wikiexplorer.R
import com.example.wikiexplorer.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")

        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Log.d("MainActivity", "ViewBinding inflated successfully")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error inflating view binding: ${e.message}")
            return
        }

        try {
            setSupportActionBar(binding.toolbar)
            Log.d("MainActivity", "Toolbar set")
        } catch (e: Exception) {
            Log.e("MainActivity", "Toolbar setup failed: ${e.message}")
        }

        try {
            binding.viewPager.adapter = ViewPagerAdapter(this)
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Random"
                    1 -> "Featured"
                    2 -> "Categories"
                    else -> "Tab"
                }
            }.attach()
            Log.d("MainActivity", "ViewPager + TabLayout configured")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in TabLayout setup: ${e.message}")
        }

        try {
            binding.navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_theme -> {
                        toggleTheme()
                        true
                    }
                    else -> false
                }
            }
            Log.d("MainActivity", "Navigation drawer listener set")
        } catch (e: Exception) {
            Log.e("MainActivity", "NavView listener setup failed: ${e.message}")
        }
    }

    private fun toggleTheme() {
        val current = AppCompatDelegate.getDefaultNightMode()
        val next = if (current == AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.MODE_NIGHT_NO
        else
            AppCompatDelegate.MODE_NIGHT_YES

        Log.d("MainActivity", "Toggling theme: $current → $next")
        AppCompatDelegate.setDefaultNightMode(next)
    }
}
