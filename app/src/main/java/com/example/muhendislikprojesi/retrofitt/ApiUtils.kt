package com.example.tokentry.retrofitt

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Servis oluşturucu
object ApiUtils {
    private const val BASE_URL = "https://trackingprojectwebappservice20240505190044.azurewebsites.net/"

    fun getAuthService(): AuthService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}