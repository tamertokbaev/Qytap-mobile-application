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
import okhttp3.*
import java.io.IOException
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CatalogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CatalogFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val client = OkHttpClient()
    private val BASE_URL = "https://tamertokbaev.kz/api"
    private var catalog_recycler: RecyclerView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_catalog, container, false)
        catalog_recycler = rootView.findViewById(R.id.catalog_recycler)
        fetchBooks()
        // Inflate the layout for this fragment
        return rootView
    }

    // This function is used for fetching books from tamertokbaev.kz backend for fragment called "Catalog"
    private fun fetchBooksForCatalogFragment(): String? {
        val request = Request.Builder()
            .url("$BASE_URL/books")
            .build()

        var responseJson : String? = null
        var errorResponse : Exception? = null
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorResponse = e
            }
            override fun onResponse(call: Call, response: Response) {
                responseJson = response.body()?.string()
            }
        })
        return responseJson
    }

    // The most actual now!!
    fun fetchBooks(){
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
                        adapter = BooksAdapter(response.body()!!)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CatalogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CatalogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}