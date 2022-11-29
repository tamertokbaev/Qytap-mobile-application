package com.tamertokbaev.qytap

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.tamertokbaev.qytap.globals.Constants

class HomeAccountFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home_account, container, false)
        val welcomeTextView = rootView.findViewById<TextView>(R.id.home_welcome_text)

        val sharedPreferences = activity?.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val email: String? = sharedPreferences?.getString(Constants.APP_SHARED_USER_EMAIL_KEY, "Empty email")
        val name: String? = sharedPreferences?.getString(Constants.APP_SHARED_USER_NAME_KEY, "Empty name")

        welcomeTextView.text = "Welcome, ${name}"

        // Inflate the layout for this fragment
        return rootView
    }
}