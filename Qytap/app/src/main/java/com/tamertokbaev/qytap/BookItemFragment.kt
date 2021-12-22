package com.tamertokbaev.qytap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tamertokbaev.qytap.models.Book

class BookItemFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("12345", view?.tag.toString())
        val rootView = inflater.inflate(R.layout.fragment_book_item, container, false)
        // Inflate the layout for this fragment
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("123", view.tag.toString())
    }

    fun getBookInformationFromBookListFragment(book: Book){
        Log.d("Book details: ", book.toString())
    }
}