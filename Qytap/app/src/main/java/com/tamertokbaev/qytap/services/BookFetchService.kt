package com.tamertokbaev.qytap.services

import com.tamertokbaev.qytap.models.BookResponse
import retrofit2.Call
import retrofit2.http.GET

interface BookFetchService {
    @GET("books")
    fun getFeaturedBooks() : Call<List<BookResponse>>
}