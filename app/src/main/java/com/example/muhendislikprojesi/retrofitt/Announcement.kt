package com.example.muhendislikprojesi.retrofitt

//Duyurular İçin Sınıf
data class Announcement(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val typeID: Int,
    val status: Boolean
)