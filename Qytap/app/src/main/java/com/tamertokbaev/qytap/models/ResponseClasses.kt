package com.tamertokbaev.qytap.models

data class UserCardResponse (
    val message: String,
    val cart: UserCard
)

data class UserCard (
    val id: Int,
    val user_id: Int,
    val number: String,
    val expire_date: String,
    val cvv: Int
)