package com.example.muhendislikprojesi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.muhendislikprojesi.PanelParts.Duyurular
import com.example.muhendislikprojesi.PanelParts.GecmisUyarilar
import com.example.muhendislikprojesi.PanelParts.KayitliCihazlar
import com.example.muhendislikprojesi.Retrofit.Comments
import com.example.muhendislikprojesi.Retrofit.MyApi
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuhendislikProjesiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SayfaGecisleri()
                }
            }
        }
    }
}

@Composable
fun SayfaGecisleri(){
    getAllComments()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoginPanel"){
        composable("LoginPanel"){
            LoginPanel(navController=navController)
        }
        composable("MainPanel"){
            MainPanel(navController = navController)
        }
            composable("Duyurular"){
                Duyurular(navController = navController)
            }
            composable("GecmisUyarilar"){
                GecmisUyarilar(navController = navController)
            }
            composable("KayitliCihazlar"){
                KayitliCihazlar(navController = navController)
            }
    }
}

//Retrofit Kısmı
private fun getAllComments(){
    val BASE_URL = "https://trackingprojectwebappservice20240505190044.azurewebsites.net/"
    val TAG:String = "CHECK_RESPONSE"
    val api = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(MyApi::class.java)

    api.getComments().enqueue(object : Callback<List<Comments>> {
        override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
            if (response.isSuccessful){
                response.body()?.let {
                    for (comment in it){
                        Log.i(TAG,"onResponse: ${comment.firstName}")
                    }
                }
            }
        }

        override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
            Log.i(TAG,"onFailure: ${t.message}")
        }
    })
}


@Preview(showBackground = true)
@Composable
fun SayfaGecisleriPreview() {
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}