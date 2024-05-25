package com.example.tokentry.retrofitt

// Kullanıcı girişi yanıtı
data class LoginResponse(
    val token: String,
    val message: String
)