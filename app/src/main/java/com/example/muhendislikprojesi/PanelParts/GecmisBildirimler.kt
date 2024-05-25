package com.example.tokentry.PanelParts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.R
import com.example.muhendislikprojesi.SayfaGecisleri
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GecmisBildirimler(navController: NavController){
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Geçmiş Bildirimler") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.Tenrengi),
                    titleContentColor = colorResource(id = R.color.white)
                )
            )
        },
        content = {
            Column (modifier = Modifier.fillMaxSize()){
                Card (modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .padding(top = 100.dp)){
                    Text(text = "Selam")
                }
                Card (modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .padding(top = 50.dp)){
                    Text(text = "Selam")
                }
                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "Geri Dön")
                }
            }
        }
    )
}

@Preview
@Composable
fun GecmisBildirimlerPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}