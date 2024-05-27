package com.example.muhendislikprojesi.retrofitt

data class Alert(
    val id: Int,
    val message: String,
    val time: Long,
    val type: String,
    val userId: Int,
    val entry: Boolean
)