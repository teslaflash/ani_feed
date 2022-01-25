package com.teslaflash.ani_feed

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.teslaflash.ani_feed.viewmodel.AnimeViewModel
import com.teslaflash.ani_feed.viewmodel.AnimeViewModelFactory

class MainActivity: AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val animeViewModel: AnimeViewModel by viewModels {
        AnimeViewModelFactory((application as AnimeApplication).repository)
    }
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(ContentValues.TAG, "onCreate: $animeViewModel")

        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment

        navController = navHostFragment.navController

        navView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.feed, R.id.history)
        )

        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onResume() {
        super.onResume()

        navController.addOnDestinationChangedListener{ navcontroller, destination, bundle ->

            when (destination.id){
                R.id.anime_details -> supportActionBar?.hide()
                R.id.navigation_feed ->  supportActionBar?.hide()
                R.id.navigation_history ->  supportActionBar?.hide()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}