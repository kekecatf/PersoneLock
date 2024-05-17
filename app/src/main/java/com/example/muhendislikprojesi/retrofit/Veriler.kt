package com.example.retrofitdeneme6.retrofit

data class ResponseMessage(
    val departmentID: Int,
    val firstName: String,
    val id: Int,
    val email: String,
    val userName: String,
    val emailConfirmed: Boolean,
    val securityStamp: String,
    val lastName:String
)