package com.example.tokentry.PanelParts

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.R
import com.example.muhendislikprojesi.SayfaGecisleri
import com.example.muhendislikprojesi.retrofitt.Alert
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.tokentry.retrofitt.ApiUtils
import com.example.tokentry.retrofitt.JWTData
import com.example.tokentry.storage.getThemePreference
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GecmisBildirimler(navController: NavController) {
    val context = LocalContext.current
    val apiService = ApiUtils.getAuthService()
    var alerts by remember { mutableStateOf<List<Alert>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val sharedPreferences = context.getSharedPreferences("jwt_prefs", Context.MODE_PRIVATE)
        val jwt = sharedPreferences.getString("jwt", null)
        val userId = jwt?.let { decodeJWT(it).userId }

        if (userId != null) {
            val call = apiService.getAlerts(userId)
            call.enqueue(object : Callback<List<Alert>> {
                override fun onResponse(call: Call<List<Alert>>, response: Response<List<Alert>>) {
                    if (response.isSuccessful) {
                        alerts = response.body()
                    } else {
                        error = "Geçmiş bildirimler yüklenemedi"
                    }
                }

                override fun onFailure(call: Call<List<Alert>>, t: Throwable) {
                    error = "Geçmiş bildirimler yüklenemedi: ${t.message}"
                }
            })
        } else {
            error = "Kullanıcı ID'si alınamadı"
        }
    }
    MuhendislikProjesiTheme(darkTheme = getThemePreference(context)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Geçmiş Bildirimler") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            content = {
                if (alerts != null) {
                    LazyColumn {
                        items(alerts!!) { alert ->
                            Text(text = alert.title, style = MaterialTheme.typography.titleLarge)
                            Text(text = alert.content)
                            Text(text = alert.date)
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                } else {
                    error?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        )
    }

}

// JWT'yi decode eden yardımcı fonksiyon
fun decodeJWT(jwt: String): JWTData {
    val parts = jwt.split(".")
    val payload = parts[1]
    val json = String(Base64.decode(payload, Base64.DEFAULT))
    return Gson().fromJson(json, JWTData::class.java)
}

@Preview
@Composable
fun GecmisBildirimlerPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}