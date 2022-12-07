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
import androidx.recyclerview.widget.RecyclerView
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.helpers.CheckoutAdapter
import com.tamertokbaev.qytap.models.UserCheckoutResponse
import com.tamertokbaev.qytap.services.CheckoutService
import com.tamertokbaev.qytap.services.ServiceBuilder

class CheckoutFragment : Fragment() {
    private val destinationService = ServiceBuilder.buildService(CheckoutService::class.java)
    private var bearerToken: String? = null
    private var catalog_recycler: RecyclerView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catalog_recycler = view.findViewById(R.id.checkout_recycler)

        getUserCheckout()
    }

    private fun getUserCheckout() {
        val preferences = view?.context?.getSharedPreferences(
            Constants.APP_SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
        this.bearerToken = preferences?.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")

        val requestCall = destinationService.getUserCheckout(this.bearerToken);

        requestCall.enqueue(object : retrofit2.Callback<UserCheckoutResponse> {
            override fun onResponse(
                call: retrofit2.Call<UserCheckoutResponse>,
                response: retrofit2.Response<UserCheckoutResponse>
            ) {
                if (response.isSuccessful) {
                    val checkoutInfo = response.body()!!
                    catalog_recycler?.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(requireContext(),1)
                        adapter = CheckoutAdapter(response.body()!!.books)
                    }


                } else {
                    Log.d("Request error", response.message())
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<UserCheckoutResponse>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(requireContext(), "Something went wrong $t", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}