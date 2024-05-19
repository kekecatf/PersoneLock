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
fun saveLoginInfo(context: Context, email: String, password: String, rememberMe: Boolean) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString("email", email)
    editor.putString("password", password)
    editor.putBoolean("rememberMe", rememberMe)
    editor.apply()
}

fun getLoginInfo(context: Context): Triple<String, String, Boolean> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
    val email = sharedPreferences.getString("email", "") ?: ""
    val password = sharedPreferences.getString("password", "") ?: ""
    val rememberMe = sharedPreferences.getBoolean("rememberMe", false)
    return Triple(email, password, rememberMe)
}

