package com.tamertokbaev.qytap.models

data class UserCardResponse(
    val message: String,
    val cart: UserCard
)

data class UserCard(
    val id: Int,
    val user_id: Int,
    val number: String,
    val full_name: String,
    val expire_date: String,
    val cvv: Int
)

data class UserCardPost(
    val number: String,
    val full_name: String,
    val expire_date: String,
    val cvv: Int
)

data class UserCheckoutResponse(
    val message: String,
    val books: ArrayList<Book>
)

data class UserCheckoutActionResponse(
    val message: String,
)