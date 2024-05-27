package com.example.muhendislikprojesi.retrofitt

data class Alert(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val typeID: Int,
    val status: Boolean
)