package com.tamertokbaev.qytap.models

data class BookOwnedResponse(
    val message: String?,
    val bought: ArrayList<Book>?,
    val fav: ArrayList<Book>?
)
