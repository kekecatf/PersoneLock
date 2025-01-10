package com.example.tokentry.PanelParts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.SayfaGecisleri
import com.example.muhendislikprojesi.retrofitt.Announcement
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.tokentry.retrofitt.ApiUtils
import com.example.tokentry.storage.getThemePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Duyurular(navController: NavController) {
    val context = LocalContext.current
    val apiService = ApiUtils.getAuthService()
    val scope = rememberCoroutineScope()
    var announcements by remember { mutableStateOf<List<Announcement>>(emptyList()) }
    var allAnnouncements by remember { mutableStateOf<List<Announcement>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showAllAnnouncements by remember { mutableStateOf(false) }

    // Duyuruları Yükleme
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = withContext(Dispatchers.IO) { apiService.getAnnouncements().execute() }
                if (response.isSuccessful) {
                    allAnnouncements = response.body()?.reversed() ?: emptyList()
                    announcements = allAnnouncements.take(5)
                } else {
                    errorMessage = "Failed to load announcements: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            }
        }
    }

    MuhendislikProjesiTheme(darkTheme = getThemePreference(context)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Duyurular") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                ) {
                    when {
                        errorMessage != null -> {
                            Text(
                                text = errorMessage!!,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        announcements.isEmpty() -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                        else -> {
                            LazyColumn {
                                items(announcements) { announcement ->
                                    AnnouncementItem(announcement)
                                }
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    ) {
                                        TextButton(
                                            onClick = {
                                                showAllAnnouncements = !showAllAnnouncements
                                                announcements = if (showAllAnnouncements) {
                                                    allAnnouncements
                                                } else {
                                                    allAnnouncements.take(5)
                                                }
                                            },
                                            modifier = Modifier.align(Alignment.Center)
                                        ) {
                                            Text(if (showAllAnnouncements) "Daralt" else "Genişlet")
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

//Dinamik Olarak Card Yapısı Oluştuan Fonksiyon
@Composable
fun AnnouncementItem(announcement: Announcement) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = announcement.title, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = announcement.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = announcement.date, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@SuppressLint("NewApi")
@Preview
@Composable
fun DuyurularPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}