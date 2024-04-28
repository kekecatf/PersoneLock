package com.example.muhendislikprojesi.PanelParts

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.muhendislikprojesi.SayfaGecisleri
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme

@Preview
@Composable
fun DuyurularPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}

@Composable
fun Duyurular(navController: NavController){
    Column {
        Text(text = "Duyurular")
    }
}