package com.tamertokbaev.qytap.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.tamertokbaev.qytap.R

class GetStartedActivity : AppCompatActivity() {
    // This piece of code is responsible for running mobile program
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    fun onClickGetStartedButton(view: View){
        val button = findViewById<Button>(R.id.getStarted)
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}