package com.example.tokentry.retrofitt

// Kullanıcı Giriş Bilgileri İçin Sınıf
data class LoginRequest(
    val email: String,
    val password: String
)