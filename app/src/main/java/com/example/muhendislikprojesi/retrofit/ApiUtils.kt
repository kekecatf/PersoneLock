package com.example.retrofitdeneme6.retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "https://trackingprojectwebappservice20240505190044.azurewebsites.net/"

        fun getVerilerDaoInterface():VerilerDaoInterface{
            return RetrofitClient.getClient(BASE_URL).
            create(VerilerDaoInterface::class.java)
        }
    }
}