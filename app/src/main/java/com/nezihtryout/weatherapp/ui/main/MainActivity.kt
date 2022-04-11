package com.nezihtryout.weatherapp.ui.main

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import com.nezihtryout.weatherapp.R


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        navigateUser()
        /*if (savedInstanceState == null) {
            // Adding Fragment
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container_view)
            }
        }*/
    }


    private fun navigateUser(){
        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        navHostFragment.navController.graph = getNavGraph(inflater)
    }

    private fun getNavGraph(inflater: NavInflater): NavGraph {
        return inflater.inflate(R.navigation.nav_graph)
    }
}