package com.tamertokbaev.qytap.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.tamertokbaev.qytap.R
import com.tamertokbaev.qytap.database.DBManager

class SignInActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    fun onCLickSignInButton(view: View){
        val emailEditText                = findViewById<EditText>(R.id.signin_email)
        val passwordEditText             = findViewById<EditText>(R.id.signin_password)

        val emailInputLayout             = findViewById<TextInputLayout>(R.id.signin_email_error)
        val passwordInputLayout             = findViewById<TextInputLayout>(R.id.signin_password_error)

        val email                        = emailEditText.text.toString()
        val password                     = passwordEditText.text.toString()

        val dbManager: DBManager = DBManager(this)
        val statusOfExecutedQuery = dbManager.checkCredentialsUser(email, password)

        if(statusOfExecutedQuery){
            // Change activity on successful registration
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            emailInputLayout.setError("Email or password is incorrect")
            passwordInputLayout.setError("Email or password is incorrect")
            // Clear text and provide some error message to user
            emailEditText.text.clear()
            passwordEditText.text.clear()
        }
    }

    fun onClickSingUpLink(view: View){
        val link = findViewById<TextView>(R.id.signUpLink)
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}