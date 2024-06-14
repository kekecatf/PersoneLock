package com.example.muhendislikprojesi.retrofitt

import com.auth0.android.jwt.JWT

//Token Çözümleme Fonksiyonu
fun decodeJWT(token: String): JWT? {
    return try {
        JWT(token)
    } catch (exception: Exception) {
        null
    }
}