package com.example.muhendislikprojesi.Retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Veriler(@SerializedName("firstName")
              @Expose
              var firstName:String,
              @SerializedName("lastName")
              @Expose
              var lastName:String,
              @SerializedName( "departmentID")
              @Expose
              var  departmentID:Int) {
}