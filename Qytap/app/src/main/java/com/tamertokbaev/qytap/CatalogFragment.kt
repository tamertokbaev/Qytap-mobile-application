package com.tamertokbaev.qytap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tamertokbaev.qytap.helpers.BooksAdapter
import com.tamertokbaev.qytap.models.BookResponse
import com.tamertokbaev.qytap.services.BookFetchService
import com.tamertokbaev.qytap.services.ServiceBuilder

class CatalogFragment : Fragment() {
    private var catalog_recycler: RecyclerView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catalog_recycler = view.findViewById(R.id.catalog_recycler)
        fetchBooks()
    }

    private fun fetchBooks(){
        //initiate the service
        val destinationService = ServiceBuilder.buildService(BookFetchService::class.java)
        val requestCall = destinationService.getFeaturedBooks()
        //make network call asynchronously
        requestCall.enqueue(object : retrofit2.Callback<BookResponse> {
            override fun onResponse(call: retrofit2.Call<BookResponse>, response: retrofit2.Response<BookResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val books = response.body()!!
                    catalog_recycler?.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(requireContext(),1)
                        adapter = BooksAdapter(response.body()!!.booksFeatured)
                    }
                }else{
                    Log.d("Request error", response.message())
                    Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: retrofit2.Call<BookResponse>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(requireContext(), "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }


}