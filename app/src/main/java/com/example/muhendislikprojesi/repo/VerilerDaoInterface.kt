package com.example.muhendislikprojesi.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.muhendislikprojesi.Retrofit.ApiUtils
import com.example.muhendislikprojesi.Retrofit.Veriler
import com.example.muhendislikprojesi.Retrofit.VerilerDaoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerilerDaoInterface {
    var verilerListesi = MutableLiveData<List<Veriler>>()
    var VerilerDaoInterface:VerilerDaoInterface

    init {
        VerilerDaoInterface = ApiUtils.getVerilerDaoInterface()
        verilerListesi = MutableLiveData()
    }

    fun kisileriGetir():MutableLiveData<List<Veriler>>{
        return verilerListesi
    }

    fun tumKisileriAl(){
        val verilerDaoInterface = ApiUtils.getVerilerDaoInterface()
        val yeniListe: MutableList<Veriler> = mutableListOf()

        verilerDaoInterface.getComments().enqueue(object : Callback<List<Veriler>> {
            override fun onResponse(call: Call<List<Veriler>>, response: Response<List<Veriler>>) {
                if (response.isSuccessful){
                    response.body()?.let { verilerListesi ->
                        // Liste null değilse buraya girer
                        yeniListe.addAll(verilerListesi)
                    }
                }
            }

            override fun onFailure(call: Call<List<Veriler>>, t: Throwable) {
                Log.i("etiket","onFailure: ${t.message}")
            }
        })
    }
}