package com.tamertokbaev.qytap.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.tamertokbaev.qytap.R
import com.tamertokbaev.qytap.database.DBManager
import com.tamertokbaev.qytap.models.UserDB

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    // This function fires when someone presses "Already have an account" link
    fun onCliCkSignInLink(view: View) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    // This function fires when someone tries to complete registration and create new User instance
    fun onCLickSignUpButton(view: View){
        val fullNameEditText             = findViewById<EditText>(R.id.fullName)
        val emailEditText                = findViewById<EditText>(R.id.email)
        val passwordEditText             = findViewById<EditText>(R.id.password)
        val passwordConfirmationEditText = findViewById<EditText>(R.id.passwordConfirmation)

        // 1 step is to take values from text fields declared in activity xml file
        val fullName                     = fullNameEditText.text.toString()
        val email                        = emailEditText.text.toString()
        val password                     = passwordEditText.text.toString()
        val passwordConfirmation         = passwordConfirmationEditText.text.toString()

        val dbManager: DBManager = DBManager(this)

        val fullNameInputLayout          = findViewById<TextInputLayout>(R.id.fullNameError)
        val emailInputLayout             = findViewById<TextInputLayout>(R.id.emailError)
        val passwordInputLayout          = findViewById<TextInputLayout>(R.id.passwordError)
        val passwordConfirmInputLayout    = findViewById<TextInputLayout>(R.id.passwordConfirmationError)

        if(email.isEmpty()) emailInputLayout.setError("Email field cannot be empty")
        else emailInputLayout.setError(null)

        if(fullName.isEmpty()) fullNameInputLayout.setError("Full Name field cannot be empty")
        else fullNameInputLayout.setError(null)

        if(password.isEmpty()) passwordInputLayout.setError("Full Name field cannot be empty")
        else passwordInputLayout.setError(null)

        if(password != passwordConfirmation) {
            passwordInputLayout.setError("Passwords do not match!")
            passwordConfirmInputLayout.setError("Passwords do not match!")
        }
        else{
            passwordInputLayout.setError(null)
            passwordConfirmInputLayout.setError(null)
        }
        if(password.length <= 6) passwordInputLayout.setError("Password length should by more than 6")
        else passwordInputLayout.setError(null)

        // TODO add validation
        // We pass null as a first parameter, because ID field will be automatically auto-incremented
        val statusOfExecutedQuery = dbManager.addNewUser(UserDB(null, fullName, email, password))
        if(statusOfExecutedQuery > -1){
            fullNameEditText.text.clear()
            emailEditText.text.clear()
            passwordEditText.text.clear()
            passwordConfirmationEditText.text.clear()
        }
    }
}
