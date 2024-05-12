package com.example.muhendislikprojesi.Retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Veriler (@SerializedName("departmentID")
               @Expose
               var departmentID:Int,
               @SerializedName("firstName")
               @Expose
               var firstName:String,
               @SerializedName("id")
               @Expose
               var id:Int){
}