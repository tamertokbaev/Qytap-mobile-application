package com.tamertokbaev.qytap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tamertokbaev.qytap.helpers.BooksAdapter
import com.tamertokbaev.qytap.models.BookResponse
import com.tamertokbaev.qytap.services.BookFetchService
import com.tamertokbaev.qytap.services.ServiceBuilder
import android.text.Editable

import android.text.TextWatcher
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask










class CatalogFragment : Fragment() {
    private var catalog_recycler: RecyclerView? = null;
    private var searchField: EditText? = null
    private val destinationService = ServiceBuilder.buildService(BookFetchService::class.java)

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
        searchField = view.findViewById(R.id.search_catalog_edit)

        searchField?.addTextChangedListener(object : TextWatcher {
            // Timer required for start searching only after certain time
            private var timer = Timer()
            private val DELAY: Long = 1000 // Milliseconds


            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                val slug = s.toString()

                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() { searchBooks(slug) }
                    },
                    DELAY
                )
            }
        })

        fetchBooks()
    }

    private fun fetchBooks(){
        //initiate the service
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

    private fun searchBooks(slug: String){
        val requestCall = destinationService.searchBooks(slug)

        requestCall.enqueue(object : retrofit2.Callback<BookResponse> {
            override fun onResponse(call: retrofit2.Call<BookResponse>, response: retrofit2.Response<BookResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val books = response.body()!!.booksFeatured
                    catalog_recycler?.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(requireContext(),1)
                        adapter = BooksAdapter(books)
                    }
                    if(books.size == 0) view?.findViewById<TextView>(R.id.catalog_search_empty)?.visibility = View.VISIBLE
                    else view?.findViewById<TextView>(R.id.catalog_search_empty)?.visibility = View.GONE
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