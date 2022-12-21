package com.tamertokbaev.qytap.services

import com.tamertokbaev.qytap.models.BookManipulation
import com.tamertokbaev.qytap.models.BookStatusResponse
import com.tamertokbaev.qytap.models.Message
import com.tamertokbaev.qytap.models.ProfileImage
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface BookManipulationService {
    @Headers("Content-Type: application/json")
    @POST("books/buy")
    fun buy(
        @Query("token") token: String?,
        @Body bookManipulation: BookManipulation
    ): Call<Message>

    @Headers("Content-Type: application/json")
    @POST("books/fav")
    fun favourite(
        @Query("token") token: String?,
        @Body bookManipulation: BookManipulation
    ): Call<Message>

    @GET("books/status")
    fun status(
        @Query("token") token: String?,
        @Query("book_id") bookId: Int
    ): Call<BookStatusResponse>

    @Multipart
    @POST("auth/profile_photo")
    fun uploadProfilePhoto(
        @Query("token") token: String?,
        @Part image: MultipartBody.Part
    ): Call<Any>
}