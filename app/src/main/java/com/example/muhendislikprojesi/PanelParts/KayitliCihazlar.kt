package com.example.muhendislikprojesi.PanelParts

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.muhendislikprojesi.SayfaGecisleri
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme

@Preview
@Composable
fun KayitliCihazlarPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}

@Composable
fun KayitliCihazlar(navController: NavController){

}