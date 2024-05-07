package com.example.muhendislikprojesi.Retrofit

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Greeting() {
    LaunchedEffect(key1 = true) {
        Log.e("deneme","123")
        tumVeriler()
    }
}

fun tumVeriler(){
    val kisilerDaoInterface = ApiUtils.getVerilerDaoInterface()

    kisilerDaoInterface.tumVeriler().enqueue(object : Callback<Veriler> {
        override fun onResponse(call: Call<Veriler>, response: Response<Veriler>) {
            val sonuc = response.body()?.departmentID

            Log.e("sonuc", sonuc.toString())
        }

        override fun onFailure(call: Call<Veriler>, t: Throwable) {

        }
    })
}