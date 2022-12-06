package com.tamertokbaev.qytap.helpers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import com.tamertokbaev.qytap.R
import com.tamertokbaev.qytap.activities.HomeActivity
import com.tamertokbaev.qytap.globals.Constants
import com.tamertokbaev.qytap.models.Book
import com.tamertokbaev.qytap.models.BookResponse

class CheckoutAdapter(private val bookList: ArrayList<Book>): RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.fragment_checkout_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.book_item_title)
        var itemGenre = itemView.findViewById<Chip>(R.id.book_item_genre)
        var imageBook = itemView.findViewById<ImageView>(R.id.book_item_image)

        // Binding fetched data to UI components from our fragment item!
        fun bind(book: Book) {
            title.text = book.name
            itemGenre.text = book.category
            Picasso.get().load(book.image).into(imageBook)


            // On click book item
            itemView.setOnClickListener {
                itemView.context
                    .getSharedPreferences(
                        Constants.APP_SHARED_PREF_NAME,
                        Context.MODE_PRIVATE
                    )
                    .edit()
                    .apply{
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

