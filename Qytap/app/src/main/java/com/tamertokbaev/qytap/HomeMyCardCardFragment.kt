package com.tamertokbaev.qytap

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.helpers.BooksAdapter
import com.tamertokbaev.qytap.models.BookResponse
import com.tamertokbaev.qytap.models.UserCardResponse
import com.tamertokbaev.qytap.services.BookFetchService
import com.tamertokbaev.qytap.services.CheckoutService
import com.tamertokbaev.qytap.services.ServiceBuilder

class HomeMyCardCardFragment: Fragment() {
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
        return inflater.inflate(R.layout.fragment_my_card_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCardInfo(view)
    }

    private fun fetchCardInfo(view: View) {
        val preferences = view.context.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        this.bearerToken = preferences.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")

        //initiate the service
        val requestCall = destinationService.getUserCard(this.bearerToken);
        //make network call asynchronously
        requestCall.enqueue(object : retrofit2.Callback<UserCardResponse> {
            override fun onResponse(call: retrofit2.Call<UserCardResponse>, response: retrofit2.Response<UserCardResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val card = response.body()!!
                    Log.d("User card", card.toString())
//                    catalog_recycler?.apply {
//                        setHasFixedSize(true)
//                        layoutManager = GridLayoutManager(requireContext(),1)
//                        adapter = BooksAdapter(response.body()!!.booksFeatured)
//                    }
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
}