package com.example.tokentry.PanelParts

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.TextButton
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
import com.example.muhendislikprojesi.retrofitt.decodeJWT
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
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GecmisBildirimler(navController: NavController, token: String?) {
    val context = LocalContext.current
    val apiService = ApiUtils.getAuthService()
    val scope = rememberCoroutineScope()
    var alerts by remember { mutableStateOf<List<Alert>>(emptyList()) }
    var allAlerts by remember { mutableStateOf<List<Alert>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showAllAlerts by remember { mutableStateOf(false) }
    var jwtData by remember { mutableStateOf<JWTData?>(null) }

    token?.let {
        jwtData = decodeJWT(it)?.let { decodedJWT ->
            JWTData(
                mail = decodedJWT.getClaim("Mail")?.asString() ?: "",
                username = decodedJWT.getClaim("Username")?.asString() ?: "",
                name = decodedJWT.getClaim("Name")?.asString() ?: "",
                department = decodedJWT.getClaim("Department")?.asString() ?: "",
                userId = decodedJWT.getClaim("UserID")?.asString() ?: "",
                role = decodedJWT.getClaim("Role")?.asString() ?: "",
                nbf = decodedJWT.getClaim("nbf")?.asLong() ?: 0L,
                exp = decodedJWT.getClaim("exp")?.asLong() ?: 0L,
                issuer = decodedJWT.issuer ?: "",
                audience = decodedJWT.audience?.joinToString(",") ?: ""
            )
        }
    }

    jwtData?.userId?.let { userId ->
        LaunchedEffect(Unit) {
            scope.launch {
                try {
                    val response = withContext(Dispatchers.IO) { apiService.getAlerts(userId).execute() }
                    if (response.isSuccessful) {
                        allAlerts = response.body()?.reversed() ?: emptyList()
                        alerts = allAlerts.take(5)
                    } else {
                        errorMessage = "Failed to load alerts: ${response.code()}"
                    }
                } catch (e: Exception) {
                    errorMessage = "Error: ${e.message}"
                }
            }
        }
    }

    MuhendislikProjesiTheme(darkTheme = getThemePreference(context)) {
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
                    when {
                        errorMessage != null -> {
                            Text(
                                text = errorMessage!!,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        alerts.isEmpty() -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        else -> {
                            LazyColumn {
                                items(alerts) { alert ->
                                    AlertItem(alert)
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    ) {
                                        TextButton(
                                            onClick = {
                                                showAllAlerts = !showAllAlerts
                                                alerts = if (showAllAlerts) {
                                                    allAlerts
                                                } else {
                                                    allAlerts.take(5)
                                                }
                                            },
                                            modifier = Modifier.align(Alignment.Center)
                                        ) {
                                            Text(if (showAllAlerts) "Daralt" else "Genişlet")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertItem(alert: Alert) {
    val formattedTime = formatUnixTimestamp(alert.time)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Mesaj: ${alert.message}")
            Text(text = "Zaman: $formattedTime")
            Text(text = "Tip: ${alert.type}")
            Text(text = "Giriş: ${if (alert.entry) "Evet" else "Hayır"}")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatUnixTimestamp(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    // Unix zaman damgasının milisaniye mi yoksa saniye mi olduğunu kontrol edelim
    val millisTimestamp = if (timestamp < 1000000000000L) timestamp * 1000 else timestamp
    return Instant.ofEpochMilli(millisTimestamp)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}

@Preview
@Composable
fun GecmisBildirimlerPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}