package com.tamertokbaev.qytap.services

import com.tamertokbaev.qytap.models.BookManipulation
import com.tamertokbaev.qytap.models.Message
import com.tamertokbaev.qytap.models.UserCardResponse
import retrofit2.Call
import retrofit2.http.*

interface CheckoutService {
    @Headers("Content-Type: application/json")
    @GET("user/get-card")
    fun getUserCard(
        @Query("token") token: String?
    ): Call<UserCardResponse>
}