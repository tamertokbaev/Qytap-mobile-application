package com.tamertokbaev.qytap.models

data class AuthResponse(
    val message: String?,
    val token: Token?,
    val user: User?
)