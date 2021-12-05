package com.tamertokbaev.qytap.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tamertokbaev.qytap.R
import okhttp3.*
import java.io.IOException
import java.lang.Exception

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

    // This function is used for fetching books from tamertokbaev.kz backend for fragment called "Catalog"
    private fun fetchBooksForCatalogFragment(){
        val request = Request.Builder()
            .url("$BASE_URL/books")
            .build()

        var responseJson : String? = null
        var errorResponse : Exception? = null
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                errorResponse = e
            }
            override fun onResponse(call: Call, response: Response) {
                responseJson = response.body()?.string()
            }
        })
        println(errorResponse)
        println(responseJson)
    }
}