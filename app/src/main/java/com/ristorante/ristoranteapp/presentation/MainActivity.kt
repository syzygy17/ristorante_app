package com.ristorante.ristoranteapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navigation_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav_view).setupWithNavController(
            navController = navController
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> setBottomNavVisibility(true)
                R.id.menuFragment -> setBottomNavVisibility(true)
                R.id.basketFragment -> setBottomNavVisibility(true)
                R.id.profileFragment -> setBottomNavVisibility(true)
                else -> setBottomNavVisibility(false)
            }
        }
    }

    private fun setBottomNavVisibility(value: Boolean) {
        findViewById<BottomNavigationView>(R.id.bottom_nav_view).isVisible = value
    }
}