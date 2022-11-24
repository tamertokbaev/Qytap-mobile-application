package com.tamertokbaev.qytap

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.helpers.BooksAdapter
import com.tamertokbaev.qytap.helpers.ReviewsAdapter
import com.tamertokbaev.qytap.models.*
import com.tamertokbaev.qytap.services.BookFetchService
import com.tamertokbaev.qytap.services.BookManipulationService
import com.tamertokbaev.qytap.services.CheckoutService
import com.tamertokbaev.qytap.services.ServiceBuilder


class CardEnterFragment : Fragment() {
    private val destinationService = ServiceBuilder.buildService(CheckoutService::class.java)

    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_book_inner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun navigateBackListener(view: View) {
        val navigateBack = view.findViewById<TextView>(R.id.navigate_back)
        navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}