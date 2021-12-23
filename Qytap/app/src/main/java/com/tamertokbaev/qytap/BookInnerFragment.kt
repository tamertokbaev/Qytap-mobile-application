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
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.helpers.BooksAdapter
import com.tamertokbaev.qytap.models.*
import com.tamertokbaev.qytap.services.BookFetchService
import com.tamertokbaev.qytap.services.BookManipulationService
import com.tamertokbaev.qytap.services.ServiceBuilder


class BookInnerFragment : Fragment() {
    private val destinationService = ServiceBuilder.buildService(BookManipulationService::class.java)
    private var book: Book? = null
    private var bearerToken: String? = null

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
        val addBookToFav    = view.findViewById<MaterialButton>(R.id.add_to_fav_btn)
        val bookBuy         = view.findViewById<MaterialButton>(R.id.buy_btn)

        val titleTextView   = view.findViewById<TextView>(R.id.book_title_inner)
        val authorTextView  = view.findViewById<TextView>(R.id.book_author_inner)
        val priceTextView   = view.findViewById<TextView>(R.id.book_price_inner)
        val ratingBar       = view.findViewById<RatingBar>(R.id.book_rating_inner)
        val typeTextView    = view.findViewById<TextView>(R.id.book_type_inner)
        val imageView       = view.findViewById<ImageView>(R.id.book_image_inner_view)

        // Getting stored book item
        val preferences = view.context.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)

        this.book = Book(
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
        this.bearerToken = preferences.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")



        // Setting up our text views with data come from preferences

        titleTextView.text  = this.book?.name
        authorTextView.text = this.book?.author
        priceTextView.text  = this.book?.price.toString() + book?.currency
        ratingBar.rating    = this.book?.book_depository_stars!!
        typeTextView.text   = this.book?.category
        Picasso.get().load(this.book?.image).into(imageView)

        fetchStatusOfBook()

        addBookToFav.setOnClickListener {
            addBookToFavourites()
        }

        bookBuy.setOnClickListener{
            addBookToCart()
        }
        navigateBackListener(view)
    }

    private fun navigateBackListener(view: View) {
        val navigateBack = view.findViewById<TextView>(R.id.navigate_back)
        navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addBookToFavourites(){
        val requestCall = destinationService.favourite(this.bearerToken, BookManipulation(this.book?.id))

        requestCall.enqueue(object: retrofit2.Callback<Message>{
            override fun onResponse(call: retrofit2.Call<Message>, response: retrofit2.Response<Message>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    if(response.body()?.message == "added"){
                        fetchStatusOfBook()
                        Toast.makeText(requireContext(), "Book added to favourite list", Toast.LENGTH_LONG).show()
                    }
                    else if(response.body()?.message == "removed"){
                        fetchStatusOfBook()
                        Toast.makeText(requireContext(), "Book removed from favourite list", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Log.d("Request error", response.message())
                    Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: retrofit2.Call<Message>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(requireContext(), "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addBookToCart(){
        val token = this.bearerToken
        val bookId = this.book?.id
        val requestCall = destinationService.buy(token, BookManipulation(bookId))

        requestCall.enqueue(object: retrofit2.Callback<Message>{
            override fun onResponse(call: retrofit2.Call<Message>, response: retrofit2.Response<Message>) {
                Log.d("Response", "onResponse: ${response.body()}")
                Log.d("Used token","$token $bookId")
                if (response.isSuccessful){
                    if(response.body()?.message == "success"){
                        fetchStatusOfBook()
                        Toast.makeText(requireContext(), "You've been purchased this book", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Log.d("Request error", response.message())
                    Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: retrofit2.Call<Message>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(requireContext(), "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchStatusOfBook(){
        val requestCall = destinationService.status(this.bearerToken, this.book?.id!!)
        val buyButton = view?.findViewById<MaterialButton>(R.id.buy_btn)
        val favButton = view?.findViewById<MaterialButton>(R.id.add_to_fav_btn)

        requestCall.enqueue(object: retrofit2.Callback<BookStatusResponse>{
            override fun onResponse(call: retrofit2.Call<BookStatusResponse>, response: retrofit2.Response<BookStatusResponse>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    if(response.body()?.message == "success"){
                        if(response.body()?.bookBoughtStatus == true) {
                            buyButton?.text = "Bought"
                            buyButton?.isEnabled = false
                        }

                        if(response.body()?.bookFavStatus == true) {
                            favButton?.text = "Remove"
                        }
                        else{
                            favButton?.text = "Add to favs"
                        }
                    }
                    else{
                        Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Log.d("Request error", response.message())
                    Toast.makeText(requireContext(), "Something went wrong ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: retrofit2.Call<BookStatusResponse>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(requireContext(), "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
}