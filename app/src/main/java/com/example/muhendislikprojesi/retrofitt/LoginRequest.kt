package com.example.tokentry.retrofitt

// Kullanıcı girişi için kullanılacak model
data class LoginRequest(
    val email: String,
    val password: String
)