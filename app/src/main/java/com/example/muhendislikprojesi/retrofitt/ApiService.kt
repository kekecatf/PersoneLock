package com.example.tokentry.retrofitt

import com.example.muhendislikprojesi.retrofitt.Alert
import com.example.muhendislikprojesi.retrofitt.Announcement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//Web Servisler
interface AuthService {
    @POST("api/Auth/mobileLogin")
    fun mobileLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("api/Announcement")
    fun getAnnouncements(): Call<List<Announcement>>

    @GET("api/Alert/getAlerts/{userId}")
    fun getAlerts(@Path("userId") userId: String): Call<List<Alert>>
}