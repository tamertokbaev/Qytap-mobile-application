package com.tamertokbaev.qytap.helpers

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
import com.tamertokbaev.qytap.models.Book
import com.tamertokbaev.qytap.models.BookResponse

class BooksAdapter(private val bookList: BookResponse): RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.fragment_book_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "Books count: ${bookList.booksFeatured.size}. Books: ${bookList.booksFeatured}")

        holder.bind(bookList.booksFeatured[position])
    }

    override fun getItemCount(): Int {
        return bookList.booksFeatured.size
    }

    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var title       = itemView.findViewById<TextView>(R.id.book_item_title)
        var itemGenre   = itemView.findViewById<Chip>(R.id.book_item_genre)
        var rating      = itemView.findViewById<RatingBar>(R.id.book_item_rating)
        var downloads   = itemView.findViewById<TextView>(R.id.book_item_downloads)
        var imageBook   = itemView.findViewById<ImageView>(R.id.book_item_image)

        // Binding fetched data to UI components from our fragment item!
        fun bind(book: Book) {
            title.text          = book.name
            itemGenre.text      = book.category
            rating.rating       = book.book_depository_stars!!
            downloads.text      = book.downloads.toString()
            Picasso.get().load(book.image).into(imageBook)

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            Navigation.findNavController(view).navigate(R.id.action_book_inner)
        }
    }
}