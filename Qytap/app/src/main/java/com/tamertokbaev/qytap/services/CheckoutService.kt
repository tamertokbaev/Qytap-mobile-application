package com.tamertokbaev.qytap.services

import com.tamertokbaev.qytap.models.*
import retrofit2.Call
import retrofit2.http.*

interface CheckoutService {
    @Headers("Content-Type: application/json")
    @GET("user/get-card")
    fun getUserCard(
        @Query("token") token: String?
    ): Call<UserCardResponse>

    @Headers("Content-Type: application/json")
    @POST("user/attach-card")
    fun attachUserCard(
        @Query("token") token: String?, @Body card: UserCardPost
    ): Call<UserCardResponse>

    @Headers("Content-Type: application/json")
    @GET("user/checkout")
    fun getUserCheckout(
        @Query("token") token: String?
    ): Call<UserCheckoutResponse>

    @Headers("Content-Type: application/json")
    @GET("user/increase")
    fun increaseQuantityCart(
        @Query("book_id") book_id: Int?,
        @Query("token") token: String?
    ): Call<UserCheckoutActionResponse>

    @Headers("Content-Type: application/json")
    @GET("user/decrease")
    fun decreaseQuantityCart(
        @Query("book_id") book_id: Int?,
        @Query("token") token: String?
    ): Call<UserCheckoutActionResponse>
}