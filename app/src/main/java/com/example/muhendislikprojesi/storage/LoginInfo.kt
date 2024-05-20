package com.example.muhendislikprojesi.storage

import android.content.Context
import android.content.SharedPreferences

// Tema tercihini kaydetmek ve almak için fonksiyonlar
fun saveThemePreference(context: Context, isDarkTheme: Boolean) {
    val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean("dark_theme", isDarkTheme)
        apply()
    }
}
fun getThemePreference(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("dark_theme", false)
}

// Login bilgilerini kaydetmek ve almak için fonksiyonlar
fun saveLoginPreferences(context: Context, username: String, password: String, rememberMe: Boolean) {
    val sharedPref = context.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putString("username", username)
        putString("password", password)
        putBoolean("rememberMe", rememberMe)
        apply()
    }
}

fun getLoginPreferences(context: Context): Map<String, Any?> {
    val sharedPref = context.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE)
    val username = sharedPref.getString("username", null)
    val password = sharedPref.getString("password", null)
    val rememberMe = sharedPref.getBoolean("rememberMe", false)
    return mapOf("username" to username, "password" to password, "rememberMe" to rememberMe)
}

fun clearLoginPreferences(context: Context) {
    val sharedPref = context.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        clear()
        apply()
    }
}

