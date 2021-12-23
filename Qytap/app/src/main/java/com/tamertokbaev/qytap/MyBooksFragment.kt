package com.tamertokbaev.qytap

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.helpers.BooksAdapter
import com.tamertokbaev.qytap.models.BookOwnedResponse
import com.tamertokbaev.qytap.models.BookResponse
import com.tamertokbaev.qytap.services.BookFetchService
import com.tamertokbaev.qytap.services.BookManipulationService
import com.tamertokbaev.qytap.services.ServiceBuilder

class MyBooksFragment : Fragment() {
    private val destinationService = ServiceBuilder.buildService(BookFetchService::class.java)
    private var bearerToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preferences = view.context.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        this.bearerToken = preferences.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")

        val boughtBooksRecyclerView = view.findViewById<RecyclerView>(R.id.my_books_bought)
        val favouriteBooksRecyclerView = view.findViewById<RecyclerView>(R.id.my_books_favourites)

        fetchBoughtBooks(boughtBooksRecyclerView, favouriteBooksRecyclerView)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun fetchBoughtBooks(recyclerViewBought: RecyclerView, recyclerViewFav: RecyclerView){
        //initiate the service
        val requestCall = this.destinationService.getOwnedBooks(this.bearerToken!!)
        //make network call asynchronously
        requestCall.enqueue(object : retrofit2.Callback<BookOwnedResponse> {
            override fun onResponse(call: retrofit2.Call<BookOwnedResponse>, response: retrofit2.Response<BookOwnedResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    if(response.body()!!.message == "success"){
                        recyclerViewBought.apply {
                            setHasFixedSize(true)
                            layoutManager = GridLayoutManager(requireContext(),1)
                            adapter = BooksAdapter(response.body()!!.bought!!)
                        }
                        recyclerViewFav.apply {
                            setHasFixedSize(true)
                            layoutManager = GridLayoutManager(requireContext(),1)
                            adapter = BooksAdapter(response.body()!!.fav!!)
                        }
                        if(response.body()!!.bought!!.size == 0) view?.findViewById<TextView>(R.id.my_books_bought_empty)?.visibility = View.VISIBLE
                        if(response.body()!!.fav!!.size == 0) view?.findViewById<TextView>(R.id.my_books_favourites_empty)?.visibility = View.VISIBLE
                    }
                    else Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }else{
                    Log.d("Request error", response.message())
                    Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: retrofit2.Call<BookOwnedResponse>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(requireContext(), "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}