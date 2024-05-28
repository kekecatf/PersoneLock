package com.example.muhendislikprojesi

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import com.auth0.android.jwt.JWT
import com.example.muhendislikprojesi.retrofitt.decodeJWT
import com.example.muhendislikprojesi.ui.theme.ThemedLogo
import com.example.tokentry.retrofitt.ApiUtils
import com.example.tokentry.retrofitt.JWTData
import com.example.tokentry.storage.clearLoginPreferences
import com.example.tokentry.storage.getThemePreference
import com.example.tokentry.storage.saveThemePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.NetworkInterface
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPanel(navController: NavController, token: String?) {
    val context = LocalContext.current
    var jwtData by remember { mutableStateOf<JWTData?>(null) }
    val focusManager = LocalFocusManager.current

    val isSystemDarkTheme = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(getThemePreference(context) || isSystemDarkTheme) }

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
    MuhendislikProjesiTheme(darkTheme = isDarkTheme) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                TopAppBar(
                    title = {
                        ThemedLogo(darkTheme = isDarkTheme,150)
                    },
                    actions = {
                        var expanded by remember { mutableStateOf(false) }
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = { navController.navigate("Duyurular") },
                                text = { Text(text = "Duyurular") }
                            )
                            DropdownMenuItem(
                                onClick = { navController.navigate("GecmisBildirimler/$token") },
                                text = { Text(text = "GecmisBildirimler") }
                            )
                            DropdownMenuItem(
                                onClick = { navController.navigate("KayitliCihazlar") },
                                text = { Text(text = "KayitliCihazlar") }
                            )
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.biyometrik),
                        contentDescription = "",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(75.dp))
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.onBackground,
                                RoundedCornerShape(75.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            jwtData?.let { data ->
                                Text(text = "Kullanıcı İsmi: ${data.name}",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold)
                                Text(text = "Departman: ${data.department}",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 18.sp)
                                Text(text = " Kullanıcı ID: ${data.userId}",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 14.sp)
                                Text(text = "E-Posta: ${data.mail}",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 14.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = {
                            isDarkTheme = it
                            saveThemePreference(context, it)
                        }
                    )

                    // Çıkış Butonu
                    Button(
                        onClick = {
                            // "Beni Hatırla" işlevini sıfırla
                            clearLoginPreferences(context)
                            // LoginPanel'e dön
                            navController.navigate("LoginPanel")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Hesaptan Çık")
                    }
                }
            }
        )
    }
}



@Preview
@Composable
fun MainPanelPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}