package com.example.tokentry.retrofitt

data class JWTData(
    val mail: String,
    val username: String,
    val name: String,
    val role: String,
    val userId: String,
    val nbf: Long,
    val exp: Long,
    val issuer: String,
    val audience: String
)