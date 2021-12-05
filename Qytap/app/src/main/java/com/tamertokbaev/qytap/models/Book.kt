package com.tamertokbaev.qytap.models

data class Book (
    val id: Int?,
    val authorGender: String,
    val authorGenres: String,
    val authorId: Int,
    val authorName: String,
    val authorPageURL: String,
    val authorRatingCount: Int,
    val authorReviewCount: Int,
    val birthplace: String,
    val bookAverageRating: Double,
    val bookFullURL: String,
    val bookId: Int,
    val bookTitle: String,
    val genre1: String,
    val genre2: String,
    val numRatings: Int,
    val numReviews: Int,
    val pages: Int,
    val publishDate: String,
    val score: Int,
    val authorAverageRating: Double
)