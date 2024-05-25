package com.example.tokentry.retrofitt

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/Auth/mobileLogin")
    fun mobileLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>
}