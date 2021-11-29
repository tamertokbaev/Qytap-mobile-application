package com.tamertokbaev.qytap.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.tamertokbaev.qytap.R
import com.tamertokbaev.qytap.database.DBManager
import com.tamertokbaev.qytap.models.User

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

        // TODO add validation
        // We pass null as a first parameter, because ID field will be automatically auto-incremented
        val statusOfExecutedQuery = dbManager.addNewUser(User(null, fullName, email, password))
        if(statusOfExecutedQuery > -1){
            fullNameEditText.text.clear()
            emailEditText.text.clear()
            passwordEditText.text.clear()
            passwordConfirmationEditText.text.clear()
        }
    }
}
