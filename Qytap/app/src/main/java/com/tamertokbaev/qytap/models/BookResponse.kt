package com.tamertokbaev.qytap.models

data class BookResponse(
    val message: String,
    val booksFeatured: ArrayList<Book>,
    val genres: ArrayList<Genre>
)
