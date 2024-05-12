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
import com.example.muhendislikprojesi.Retrofit.ApiUtils
import com.example.muhendislikprojesi.Retrofit.Veriler
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    tumVeriler()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoginPanel"){
        composable("LoginPanel"
        ){
            LoginPanel(navController=navController)
        }
        composable("MainPanel"
            ){
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
fun tumVeriler(){
    val kisilerDaoInterface = ApiUtils.getVerilerDaoInterface()

    kisilerDaoInterface.getComments().enqueue(object : Callback<List<Veriler>> {
        override fun onResponse(call: Call<List<Veriler>>, response: Response<List<Veriler>>) {
            if (response.isSuccessful){
                response.body()?.let {
                    for (comment in it){
                        Log.i("etiket","onResponse: ${comment.id}")
                    }
                }
            }
        }

        override fun onFailure(call: Call<List<Veriler>>, t: Throwable) {
            Log.i("etiket","onFailure: ${t.message}")
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