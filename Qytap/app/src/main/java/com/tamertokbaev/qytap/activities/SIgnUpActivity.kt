package com.tamertokbaev.qytap.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tamertokbaev.qytap.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
    }


    fun onCliCkSignInLink(view: View) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}
