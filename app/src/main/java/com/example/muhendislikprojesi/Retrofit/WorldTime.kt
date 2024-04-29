package com.example.muhendislikprojesi.Retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WorldTime(@SerializedName("utc_datetime")
                     @Expose
                     var tamTarih:String,
                     @SerializedName("day_of_week")
                     @Expose
                     var haftanınGunu:Int,
                     @SerializedName("week_number")
                     @Expose
                     var haftaNumarasi:Int) {
}