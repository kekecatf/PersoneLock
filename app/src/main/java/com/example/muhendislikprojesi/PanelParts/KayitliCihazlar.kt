package com.example.muhendislikprojesi.PanelParts

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.R
import com.example.muhendislikprojesi.SayfaGecisleri
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme

@Preview
@Composable
fun LoginPanelPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri ()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KayitliCihazlar(navController: NavController){
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Kayıtlı Cihazlar") },
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
                    Text(text = "Samsung Galaxy S3 Mini\n<>Cihaz ip adresi>", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
                }

                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "Geri Dön")
                }
            }
        }
    )
}