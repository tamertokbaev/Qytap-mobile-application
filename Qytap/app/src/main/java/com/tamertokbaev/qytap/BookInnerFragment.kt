package com.tamertokbaev.qytap

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.models.Book


class BookInnerFragment : Fragment() {

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
        val titleTextView   = view.findViewById<TextView>(R.id.book_title_inner)
        val authorTextView  = view.findViewById<TextView>(R.id.book_author_inner)
        val priceTextView   = view.findViewById<TextView>(R.id.book_price_inner)
        val ratingBar       = view.findViewById<RatingBar>(R.id.book_rating_inner)
        val typeTextView    = view.findViewById<TextView>(R.id.book_type_inner)
        val imageView       = view.findViewById<ImageView>(R.id.book_image_inner_view)

        // Getting stored book item
        val preferences = view.context.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val book = Book(
            id = preferences.getInt(Constants.APP_SHARED_BOOK_ID_KEY, 1),
            name = preferences.getString(Constants.APP_SHARED_BOOK_NAME_KEY, "undefined"),
            author = preferences.getString(Constants.APP_SHARED_BOOK_AUTHOR_KEY, "undefined"),
            format = preferences.getString(Constants.APP_SHARED_BOOK_FORMAT_KEY, "undefined"),
            book_depository_stars = preferences.getFloat(Constants.APP_SHARED_BOOK_RATING_KEY, 0F),
            price = preferences.getFloat(Constants.APP_SHARED_BOOK_PRICE_KEY, 0F).toDouble(),
            currency = preferences.getString(Constants.APP_SHARED_BOOK_CURRENCY_KEY, ""),
            category = preferences.getString(Constants.APP_SHARED_BOOK_CATEGORY_KEY, "undefined"),
            image = preferences.getString(Constants.APP_SHARED_BOOK_IMAGE_KEY, ""),
            downloads = preferences.getInt(Constants.APP_SHARED_BOOK_DOWNLOADS_KEY, 0),
            created_at = null,
            updated_at = null,
            old_price = null,
            img_paths = null,
            isbn = null
        )

        // Setting up our text views with data come from preferences

        titleTextView.text  = book.name
        authorTextView.text = book.author
        priceTextView.text  = book.price.toString() + book.currency
        ratingBar.rating    = book.book_depository_stars!!
        typeTextView.text   = book.category
        Picasso.get().load(book.image).into(imageView)
    }
}