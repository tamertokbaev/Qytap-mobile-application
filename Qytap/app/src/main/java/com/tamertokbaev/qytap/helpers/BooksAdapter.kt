package com.tamertokbaev.qytap.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tamertokbaev.qytap.R
import com.tamertokbaev.qytap.models.Book
import com.tamertokbaev.qytap.models.BookResponse

class BooksAdapter(private val bookList: List<Book>): RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.fragment_catalog_book,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Response", "Books count: ${bookList.size}. Books: ${bookList}")

        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        var title       = itemView.findViewById<TextView>(R.id.book_item_title)
        var itemGenre   = itemView.findViewById<TextView>(R.id.book_item_genre)
        var publishDate = itemView.findViewById<TextView>(R.id.book_item_publish_date)

        // Binding fetched data to UI components from our fragment item!
        fun bind(book: Book) {
            title.text          = book.bookTitle
            itemGenre.text      = book.genre1
            publishDate.text    = book.publishDate
        }
    }
}