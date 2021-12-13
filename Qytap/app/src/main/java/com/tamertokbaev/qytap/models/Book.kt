package com.tamertokbaev.qytap.models

data class Book(
    val author_average_rating: String?,
    val author_gender: String?,
    val author_genres: String?,
    val author_id: Int?,
    val author_name: String?,
    val author_page_url: String?,
    val author_rating_count: Int?,
    val author_review_count: Int?,
    val birthplace: String?,
    val book_average_rating: String?,
    val book_fullurl: String?,
    val book_id: String?,
    val book_title: String?,
    val genre_1: String?,
    val genre_2: String?,
    val id: Int?,
    val num_ratings: Int?,
    val num_reviews: Int?,
    val pages: String?,
    val publish_date: String?,
    val score: Int?
)