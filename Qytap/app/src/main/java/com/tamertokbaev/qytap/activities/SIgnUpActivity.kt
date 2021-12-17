package com.tamertokbaev.qytap.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.tamertokbaev.qytap.R
import com.tamertokbaev.qytap.database.DBManager
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.models.AuthResponse
import com.tamertokbaev.qytap.models.User
import com.tamertokbaev.qytap.models.UserDB
import com.tamertokbaev.qytap.services.AuthService
import com.tamertokbaev.qytap.services.ServiceBuilder

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
    fun onCLickSignUpButton(view: View) {
        val fullNameEditText = findViewById<EditText>(R.id.fullName)
        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val passwordConfirmationEditText = findViewById<EditText>(R.id.passwordConfirmation)

        // 1 step is to take values from text fields declared in activity xml file
        val fullName = fullNameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val passwordConfirmation = passwordConfirmationEditText.text.toString()

        val fullNameInputLayout = findViewById<TextInputLayout>(R.id.fullNameError)
        val emailInputLayout = findViewById<TextInputLayout>(R.id.emailError)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordError)
        val passwordConfirmInputLayout =
            findViewById<TextInputLayout>(R.id.passwordConfirmationError)

        if (email.isEmpty()) emailInputLayout.setError("Email field cannot be empty")
        else emailInputLayout.setError(null)

        if (fullName.isEmpty()) fullNameInputLayout.setError("Full Name field cannot be empty")
        else fullNameInputLayout.setError(null)

        if (password.isEmpty()) passwordInputLayout.setError("Full Name field cannot be empty")
        else passwordInputLayout.setError(null)

        if (password != passwordConfirmation) {
            passwordInputLayout.setError("Passwords do not match!")
            passwordConfirmInputLayout.setError("Passwords do not match!")
        } else {
            passwordInputLayout.setError(null)
            passwordConfirmInputLayout.setError(null)
        }
        if (password.length <= 6) passwordInputLayout.setError("Password length should by more than 6")
        else passwordInputLayout.setError(null)

//        // We pass null as a first parameter, because ID field will be automatically auto-incremented
//        val statusOfExecutedQuery = dbManager.addNewUser(UserDB(null, fullName, email, password))
//        if(statusOfExecutedQuery > -1){
//            fullNameEditText.text.clear()
//            emailEditText.text.clear()
//            passwordEditText.text.clear()
//            passwordConfirmationEditText.text.clear()
//        }

        if (fullNameInputLayout.error == null &&
            emailInputLayout.error == null &&
            passwordInputLayout.error == null &&
            passwordConfirmInputLayout.error == null
        ) {
            val destinationService = ServiceBuilder.buildService(AuthService::class.java)
            val requestCall =
                destinationService.signUp(User(name = fullName, email = email, password = password))
            requestCall.enqueue(object : retrofit2.Callback<AuthResponse> {
                override fun onResponse(
                    call: retrofit2.Call<AuthResponse>,
                    response: retrofit2.Response<AuthResponse>
                ) {
                    Log.d("Response", "onResponse: ${response.body()}")
                    if (response.isSuccessful) {
                        val authResponse: AuthResponse = response.body()!!
                        val userData = authResponse.user
                        val token = authResponse.token

                        if (authResponse.message == "success") {
                            // Shared preferences is used to store some state globally inside application
                            // In this case, I'm going to store user data globally, so I can then get in in any activity I need
                            val sharedPreferences = getSharedPreferences(
                                Constants.APP_SHARED_PREF_NAME,
                                Context.MODE_PRIVATE
                            )
                            val editor = sharedPreferences.edit()
                            editor.apply {
                                putString(Constants.APP_SHARED_USER_EMAIL_KEY, userData?.email)
                                putString(Constants.APP_SHARED_USER_NAME_KEY, userData?.name)
                                putString(Constants.APP_SHARED_USER_TOKEN_KEY, token?.access_token)
                            }.apply()
                            // Change activity on successful sign up
                            val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.d("Request error", response.message())
                        Toast.makeText(this@SignUpActivity, response.message(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<AuthResponse>, t: Throwable) {
                    Log.d("Exception", "Occurred exception: ${t}")
                    Toast.makeText(
                        this@SignUpActivity,
                        "Something went wrong $t",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}
