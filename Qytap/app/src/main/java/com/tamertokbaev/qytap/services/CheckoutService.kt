package com.tamertokbaev.qytap.services

import com.tamertokbaev.qytap.models.BookManipulation
import com.tamertokbaev.qytap.models.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface CheckoutService {
    @Headers("Content-Type: application/json")
    @POST("books/buy")
    fun buy(
        @Query("token") token: String?,
        @Body bookManipulation: BookManipulation
    ): Call<Message>
}