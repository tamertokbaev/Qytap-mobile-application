package com.tamertokbaev.qytap.models

data class Book(
    val author: String?,
    val book_depository_stars: Float?,
    val category: String?,
    val created_at: String?,
    val currency: String?,
    val downloads: Int?,
    val format: String?,
    val id: Int?,
    val image: String?,
    val img_paths: String?,
    val isbn: Long?,
    val name: String?,
    val old_price: Double?,
    val price: Double?,
    val updated_at: String?
)