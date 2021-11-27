package com.tamertokbaev.qytap.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tamertokbaev.qytap.CatalogFragment
import com.tamertokbaev.qytap.HomeFragment
import com.tamertokbaev.qytap.MyBooksFragment
import com.tamertokbaev.qytap.R

class HomeActivity : AppCompatActivity(){
    val catalogFragment: Fragment = CatalogFragment()
    val myBooksFragment: Fragment = MyBooksFragment()
    val homeFragment: Fragment = HomeFragment()
    val fragmentManager: FragmentManager = supportFragmentManager

    // Setting active fragment to display it as first
    val activeFragment: Fragment = homeFragment;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView: BottomNavigationView = findViewById(R.id.navigation)

    }


}