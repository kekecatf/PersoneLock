package com.example.tokentry.retrofitt

//Kullanıcı Girişi Yanıtı İçin Sınıf
data class LoginResponse(
    val token: String,
    val message: String
)