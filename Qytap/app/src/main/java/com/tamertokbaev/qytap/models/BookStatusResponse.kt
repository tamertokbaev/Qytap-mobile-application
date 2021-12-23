package com.tamertokbaev.qytap.models

data class BookStatusResponse(
    val message: String?,
    val error: String?,
    val bookBoughtStatus: Boolean?,
    val bookFavStatus: Boolean?
)
