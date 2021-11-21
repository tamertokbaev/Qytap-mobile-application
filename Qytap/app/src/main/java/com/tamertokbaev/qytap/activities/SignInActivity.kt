package com.tamertokbaev.qytap.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tamertokbaev.qytap.R

class SignInActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    fun onClickSingUpLink(view: View){
        val link = findViewById<TextView>(R.id.signUpLink)
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}