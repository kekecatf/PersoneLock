package com.example.muhendislikprojesi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.muhendislikprojesi.PanelParts.Duyurular
import com.example.muhendislikprojesi.PanelParts.GecmisUyarilar
import com.example.muhendislikprojesi.PanelParts.KayitliCihazlar
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.retrofitdeneme6.retrofit.ApiResponse
import com.example.retrofitdeneme6.retrofit.ApiUtils
import com.example.retrofitdeneme6.retrofit.ResponseMessage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuhendislikProjesiTheme {
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

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoginPanel"){
        composable("LoginPanel"
        ){
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


@Preview(showBackground = true)
@Composable
fun SayfaGecisleriPreview() {
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}