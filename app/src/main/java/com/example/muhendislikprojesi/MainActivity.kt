package com.example.muhendislikprojesi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.muhendislikprojesi.PanelParts.Duyurular
import com.example.muhendislikprojesi.PanelParts.GecmisUyarilar
import com.example.muhendislikprojesi.PanelParts.KayitliCihazlar
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SayfaGecisleri()
        }
    }
}

@Composable
fun SayfaGecisleri(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoginPanel"){
        composable("LoginPanel"
        ){
            LoginPanel(navController=navController)
        }
        composable("MainPanel"){
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


@Preview(showBackground = true)
@Composable
fun SayfaGecisleriPreview() {
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}