package com.example.muhendislikprojesi.retrofitt

data class Announcement(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val typeID: Int,
    val status: Boolean
)