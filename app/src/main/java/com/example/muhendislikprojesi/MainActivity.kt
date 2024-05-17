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
                    SayfaGecisleri { departmentID, firstName, id, email, userName, emailConfirmed, securityStamp ->
                    }
                }
            }
        }
    }
}

@Composable
fun SayfaGecisleri(onDataReceived: (Int, String, Int, String, String, Boolean,String) -> Unit){

    //    var departmentID by remember { mutableStateOf(0) }
//    var firstName by remember { mutableStateOf("") }
//    var id by remember { mutableStateOf(0) }
//    var email by remember { mutableStateOf("") }
//    var userName by remember { mutableStateOf("") }
//    var emailConfirmed by remember { mutableStateOf(false) }
//    var securityStamp by remember { mutableStateOf("") }
//
//    LaunchedEffect(Unit) {
//        val sonuc = getVeri()
//        if (sonuc != null) {
//            departmentID = sonuc.departmentID
//            firstName = sonuc.firstName
//            id = sonuc.id
//            email = sonuc.email
//            userName = sonuc.userName
//            emailConfirmed = sonuc.emailConfirmed
//            securityStamp = sonuc.securityStamp
//
//            onDataReceived(departmentID, firstName, id, email, userName, emailConfirmed,securityStamp)
//        } else {
//            // Hata durumu
//            Log.e("AnaFonksiyon", "Veriler alınamadı")
//        }
//
//    }


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
//RETROFİT KISMI
//Get İşlemi
suspend fun getVeri(): ResponseMessage? {
    return suspendCoroutine { continuation ->
        val kisilerDaoInterface = ApiUtils.getVerilerDaoInterface()
        kisilerDaoInterface.getComments().enqueue(object : Callback<List<ResponseMessage>> {
            // onResponse fonksiyonu içinde atama işlemleri
            override fun onResponse(call: Call<List<ResponseMessage>>, response: Response<List<ResponseMessage>>) {
                if (response.isSuccessful) {
                    val verilerListesi = response.body()
                    if (!verilerListesi.isNullOrEmpty()) {
                        // İlk Veriler nesnesine erişme
                        val veri = verilerListesi[0]
                        val departmentID = veri.departmentID
                        val firstName = veri.firstName
                        val id = veri.id
                        val email = veri.email
                        val userName = veri.userName
                        val emailConfirmed = veri.emailConfirmed
                        val securityStamp = veri.securityStamp
                        // Yapılacak işlemler...
                        continuation.resume(ResponseMessage(departmentID, firstName, id, email, userName, emailConfirmed,securityStamp))
                    }
                }
            }

            override fun onFailure(call: Call<List<ResponseMessage>>, t: Throwable) {
                Log.i("etiket","onFailure: ${t.message}")
                continuation.resume(null) // Hata durumunda null döndürme
            }
        })
    }
}




@Preview(showBackground = true)
@Composable
fun SayfaGecisleriPreview() {
    MuhendislikProjesiTheme {
        SayfaGecisleri { departmentID, firstName, id, email, userName, emailConfirmed,securityStamp ->
            Log.d("MainActivity", "departmentID: $departmentID, firstName: $firstName, id: $id, email: $email, userName: $userName, emailConfirmed: $emailConfirmed")}
    }
}