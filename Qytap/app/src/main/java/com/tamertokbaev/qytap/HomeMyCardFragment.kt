package com.tamertokbaev.qytap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tamertokbaev.qytap.activities.HomeActivity
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.models.*
import com.tamertokbaev.qytap.services.AuthService
import com.tamertokbaev.qytap.services.CheckoutService
import com.tamertokbaev.qytap.services.ServiceBuilder

class HomeMyCardFragment: Fragment() {
    private val destinationService = ServiceBuilder.buildService(CheckoutService::class.java)
    private var bearerToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.card_save_button)
            .setOnClickListener {
                onSaveCard()
            }

        fetchCardInfo(view)
    }

    private fun fetchCardInfo(view: View) {
        val preferences = view.context.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        this.bearerToken = preferences.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")
        Log.d("token", this.bearerToken.toString())
        //initiate the service
        val requestCall = destinationService.getUserCard(this.bearerToken);
        //make network call asynchronously
        requestCall.enqueue(object : retrofit2.Callback<UserCardResponse> {
            override fun onResponse(call: retrofit2.Call<UserCardResponse>, response: retrofit2.Response<UserCardResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val card = response.body()!!
                    Log.d("User card", card.toString())
                    Log.d("Request", call.request().headers().toString())
//                    catalog_recycler?.apply {
//                        setHasFixedSize(true)
//                        layoutManager = GridLayoutManager(requireContext(),1)
//                        adapter = BooksAdapter(response.body()!!.booksFeatured)
//                    }
                    val digitsEnterTextView = getView()?.findViewById<EditText>(R.id.digits)
                    val fullNameTextView = getView()?.findViewById<EditText>(R.id.full_name)
                    val dateValidTextView = getView()?.findViewById<EditText>(R.id.date)

                    digitsEnterTextView?.setText(card.cart.number)
                    fullNameTextView?.setText(card.cart.full_name)
                    dateValidTextView?.setText(card.cart.expire_date)


                }else{
                    Log.d("Request error", response.message())
                    Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: retrofit2.Call<UserCardResponse>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(requireContext(), "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onSaveCard() {
        val digitsEnterTextView = getView()?.findViewById<EditText>(R.id.digits)
        val fullNameTextView = getView()?.findViewById<EditText>(R.id.full_name)
        val dateValidTextView = getView()?.findViewById<EditText>(R.id.date)

        // 1 step is to take values from text fields declared in activity xml file
        val digits = digitsEnterTextView?.text.toString()
        val fullName= fullNameTextView?.text.toString()
        val dateValid = dateValidTextView?.text.toString()

        if (digits?.isEmpty()) {
            digitsEnterTextView?.setError("Cannot be empty")
        }
        if (fullName?.isEmpty()) {
            fullNameTextView?.setError("Cannot be empty")
        }
        if (dateValid?.isEmpty()) {
            dateValidTextView?.setError("Cannot be empty")
        }

        if (digitsEnterTextView?.error == null
            && fullNameTextView?.error == null
            && dateValidTextView?.error == null) {
            val destinationService = ServiceBuilder.buildService(CheckoutService::class.java)
            val requestCall =
                destinationService.attachUserCard(
                    this.bearerToken,
                    UserCardPost(number = digits, full_name = fullName, cvv = 0, expire_date = dateValid)
                )

            requestCall.enqueue(object : retrofit2.Callback<UserCardResponse> {
                override fun onResponse(
                    call: retrofit2.Call<UserCardResponse>,
                    response: retrofit2.Response<UserCardResponse>
                ) {
                    Log.d("Response", "onResponse: ${response.body()}")
                    if (response.isSuccessful) {
                        Toast.makeText(view?.context, response.message(), Toast.LENGTH_SHORT)
                    } else {
                        Log.d("Request error", response.message())
                    }
                }

                override fun onFailure(call: retrofit2.Call<UserCardResponse>, t: Throwable) {
                    Log.d("Exception", "Occurred exception: ${t}")
                    Toast.makeText(
                        view?.context,
                        "Something went wrong $t",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        }
    }
}