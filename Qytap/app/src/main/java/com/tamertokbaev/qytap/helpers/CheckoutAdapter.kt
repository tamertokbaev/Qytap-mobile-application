package com.tamertokbaev.qytap.helpers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import com.tamertokbaev.qytap.R
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.models.*
import com.tamertokbaev.qytap.services.CheckoutService
import com.tamertokbaev.qytap.services.ServiceBuilder

class CheckoutAdapter(private val bookList: ArrayList<Book>) :
    RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_checkout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.book_item_title)
        var itemGenre = itemView.findViewById<Chip>(R.id.book_item_genre)
        var imageBook = itemView.findViewById<ImageView>(R.id.book_item_image)
        var price = itemView.findViewById<TextView>(R.id.book_item_price)
        var increaseButton = itemView.findViewById<Button>(R.id.book_item_increase)
        var decreaseButton = itemView.findViewById<Button>(R.id.book_item_decrease)

        // Binding fetched data to UI components from our fragment item!
        fun bind(book: Book) {
            title.text = book.name
            itemGenre.text = book.category
            price.text = book.price.toString() + book.currency
            Picasso.get().load(book.image).into(imageBook)


            increaseButton.setOnClickListener {
                val preferences = itemView.context.getSharedPreferences(
                    Constants.APP_SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
                val bearerToken = preferences.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")

                val requestCall = ServiceBuilder.buildService(CheckoutService::class.java).increaseQuantityCart(book.id, bearerToken);

                requestCall.enqueue(object : retrofit2.Callback<UserCheckoutActionResponse> {
                    override fun onResponse(call: retrofit2.Call<UserCheckoutActionResponse>, response: retrofit2.Response<UserCheckoutActionResponse>) {
                        Log.d("Response", "onResponse: ${response.body()}")
                        if (response.isSuccessful){

                        }else{
                            Log.d("Request error", response.message())
                        }
                    }
                    override fun onFailure(call: retrofit2.Call<UserCheckoutActionResponse>, t: Throwable) {
                        Log.d("Exception", "Occurred exception: ${t}")
                    }
                })
            }

            decreaseButton.setOnClickListener {
                val preferences = itemView.context.getSharedPreferences(
                    Constants.APP_SHARED_PREF_NAME,
                    Context.MODE_PRIVATE
                )
                val bearerToken = preferences.getString(Constants.APP_SHARED_USER_TOKEN_KEY, "")

                val requestCall = ServiceBuilder.buildService(CheckoutService::class.java).decreaseQuantityCart(book.id, bearerToken);

                requestCall.enqueue(object : retrofit2.Callback<UserCheckoutActionResponse> {
                    override fun onResponse(call: retrofit2.Call<UserCheckoutActionResponse>, response: retrofit2.Response<UserCheckoutActionResponse>) {
                        Log.d("Response", "onResponse: ${response.body()}")
                        if (response.isSuccessful){

                        }else{
                            Log.d("Request error", response.message())
                        }
                    }
                    override fun onFailure(call: retrofit2.Call<UserCheckoutActionResponse>, t: Throwable) {
                        Log.d("Exception", "Occurred exception: ${t}")
                    }
                })

            }

            // On click book item
            itemView.setOnClickListener {
                itemView.context
                    .getSharedPreferences(
                        Constants.APP_SHARED_PREF_NAME,
                        Context.MODE_PRIVATE
                    )
                    .edit()
                    .apply {
                        putInt(Constants.APP_SHARED_BOOK_ID_KEY, book.id!!)
                        putString(Constants.APP_SHARED_BOOK_NAME_KEY, book.name.toString())
                        putString(Constants.APP_SHARED_BOOK_AUTHOR_KEY, book.author.toString())
                        putString(Constants.APP_SHARED_BOOK_FORMAT_KEY, book.format.toString())
                        putFloat(Constants.APP_SHARED_BOOK_PRICE_KEY, book.price!!.toFloat())
                        putString(Constants.APP_SHARED_BOOK_CURRENCY_KEY, book.currency.toString())
                        putString(Constants.APP_SHARED_BOOK_CATEGORY_KEY, book.category.toString())
                        putString(Constants.APP_SHARED_BOOK_IMAGE_KEY, book.image.toString())
                        putInt(Constants.APP_SHARED_BOOK_DOWNLOADS_KEY, book.downloads!!)
                    }
                    .apply()
                Navigation.findNavController(itemView).navigate(R.id.action_book_inner)
            }
        }
    }
}

