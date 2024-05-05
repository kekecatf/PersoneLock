package com.example.muhendislikprojesi.Retrofit

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Veriler(){
    LaunchedEffect(key1 = true) {
        VerileriGetir()
    }
}

fun VerileriGetir(){
    val WorldTimeInterface = ApiUtils.getWorldTimeInterface()

    WorldTimeInterface.getTime().enqueue(object : Callback<WorldTime>{
        override fun onResponse(call: Call<WorldTime>, response: Response<WorldTime>) {

            val tarih = response.body()?.tamTarih
            Log.e("Tarih :",tarih.toString())

        }

        override fun onFailure(call: Call<WorldTime>, t: Throwable) {}
    })
}