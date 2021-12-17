package com.tamertokbaev.qytap.services

import com.tamertokbaev.qytap.models.AuthResponse
import com.tamertokbaev.qytap.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun signIn(@Body user: User): Call<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun signUp(@Body user: User): Call<AuthResponse>
}