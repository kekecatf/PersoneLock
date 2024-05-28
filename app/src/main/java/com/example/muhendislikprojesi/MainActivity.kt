package com.example.muhendislikprojesi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.tokentry.PanelParts.Duyurular
import com.example.tokentry.PanelParts.GecmisBildirimler
import com.example.tokentry.PanelParts.KayitliCihazlar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SayfaGecisleri()
        }
    }
}

@Composable
fun SayfaGecisleri() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoginPanel") {
        composable("LoginPanel") {
            LoginPanel(navController = navController)
        }
        composable(
            route = "MainPanel/{token}",
            arguments = listOf(navArgument("token") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token")
            MainPanel(navController = navController, token = token)
        }
        composable("Duyurular") {
            Duyurular(navController = navController)
        }
        composable(
            route = "GecmisBildirimler/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token")
            GecmisBildirimler(navController = navController, token = token)
        }
        composable("KayitliCihazlar") {
            KayitliCihazlar(navController = navController)
        }
    }
}

@Preview
@Composable
fun SayfaGecisleriPreview() {
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}