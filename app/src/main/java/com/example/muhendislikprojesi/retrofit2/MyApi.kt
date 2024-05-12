package com.example.muhendislikprojesi.retrofit2

import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
    @GET("api/Employee")
    fun getComments(): Call<List<Comments>>

}