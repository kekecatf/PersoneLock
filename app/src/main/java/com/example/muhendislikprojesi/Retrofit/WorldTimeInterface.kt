package com.example.muhendislikprojesi.Retrofit

import retrofit2.Call
import retrofit2.http.GET

interface WorldTimeInterface {
    @GET("Europe/Istanbul")
    fun getTime():Call<WorldTime>
}