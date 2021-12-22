package com.tamertokbaev.qytap.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tamertokbaev.qytap.BookInnerFragment
import com.tamertokbaev.qytap.BookItemFragment
import com.tamertokbaev.qytap.R
import com.tamertokbaev.qytap.models.Book
import io.ktor.util.reflect.*
import okhttp3.*
import kotlin.reflect.typeOf

class HomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView: BottomNavigationView = findViewById(R.id.navigation)
        val navController = findNavController(findViewById(R.id.nav_host_fragment_activity_guest))

        navigationView.setupWithNavController(navController)
    }

    fun signOut(view: View) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}