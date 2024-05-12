package com.example.muhendislikprojesi.Retrofit

import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
    @GET("api/Employee")
    fun getComments(): Call<List<Comments>>

}