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
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.muhendislikprojesi.decodeJWT
import com.example.muhendislikprojesi.retrofitt.Alert
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.tokentry.retrofitt.ApiUtils
import com.example.tokentry.retrofitt.AuthService
import com.example.tokentry.retrofitt.JWTData
import com.example.tokentry.storage.getThemePreference
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GecmisBildirimler(navController: NavController, token: String?) {
    val context = LocalContext.current
    val apiService = ApiUtils.getAuthService()
    var alerts by remember { mutableStateOf<List<Alert>?>(null) }
    var jwtData by remember { mutableStateOf<JWTData?>(null) }

    token?.let {
        jwtData = decodeJWT(it)?.let { decodedJWT ->
            JWTData(
                mail = decodedJWT.getClaim("Mail")?.asString() ?: "",
                username = decodedJWT.getClaim("Username")?.asString() ?: "",
                name = decodedJWT.getClaim("Name")?.asString() ?: "",
                role = decodedJWT.getClaim("Role")?.asString() ?: "",
                userId = decodedJWT.getClaim("UserID")?.asString() ?: "",
                nbf = decodedJWT.getClaim("nbf")?.asLong() ?: 0L,
                exp = decodedJWT.getClaim("exp")?.asLong() ?: 0L,
                issuer = decodedJWT.issuer ?: "",
                audience = decodedJWT.audience?.joinToString(",") ?: ""
            )
        }
    }

    jwtData?.userId?.let { userId ->
        fetchAlerts(userId, apiService) { result ->
            alerts = result
        }
    }

    MuhendislikProjesiTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Geçmiş Bildirimler") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
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
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    alerts?.let {
                        LazyColumn {
                            items(it) { alert ->
                                AlertItem(alert)
                            }
                        }
                    } ?: run {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        )
    }
}

@Composable
fun AlertItem(alert: Alert) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Mesaj: ${alert.message}")
            Text(text = "Zaman: ${alert.time}") // Time formatlama yapabilirsiniz.
            Text(text = "Tip: ${alert.type}")
            Text(text = "Giriş: ${if (alert.entry) "Evet" else "Hayır"}")
        }
    }
}

fun fetchAlerts(userId: String, apiService: AuthService, onResult: (List<Alert>?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = apiService.getAlerts(userId).execute()
            if (response.isSuccessful) {
                val alerts = response.body()
                withContext(Dispatchers.Main) {
                    onResult(alerts)
                }
            } else {
                withContext(Dispatchers.Main) {
                    onResult(null)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onResult(null)
            }
        }
    }
}

@Preview
@Composable
fun GecmisBildirimlerPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}