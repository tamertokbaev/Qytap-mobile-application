package com.tamertokbaev.qytap.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tamertokbaev.qytap.R
import okhttp3.*

class HomeActivity : AppCompatActivity(){
    private val client = OkHttpClient()
    private val BASE_URL = "https://tamertokbaev.kz/api"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView: BottomNavigationView = findViewById(R.id.navigation)
        val navController = findNavController(findViewById(R.id.nav_host_fragment_activity_guest))

        navigationView.setupWithNavController(navController)
    }

    fun signOut(view: View){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}