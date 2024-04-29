package com.example.muhendislikprojesi.Retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "http://worldtimeapi.org/api/timezone/"

        fun getWorldTimeInterface():WorldTimeInterface{
            return RetrofitClient.getClient(BASE_URL).create(WorldTimeInterface::class.java)
        }
    }
}