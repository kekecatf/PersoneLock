package com.example.retrofitdeneme6.retrofit

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VerilerDaoInterface {
    @GET("api/Employee")
    fun getComments(): Call<List<ResponseMessage>>

    @POST("/api/Auth/mobileLogin")
    fun addVeri(@Body veri: RequestBody): Call<ApiResponse> // Yeni bir veri eklemek için POST isteği

}