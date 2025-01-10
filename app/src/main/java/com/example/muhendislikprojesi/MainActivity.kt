package com.example.muhendislikprojesi

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.muhendislikprojesi.PanelParts.WebGorunumu
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.tokentry.PanelParts.Duyurular
import com.example.tokentry.PanelParts.GecmisBildirimler

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SayfaGecisleri()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
            arguments = listOf(navArgument("token") {
                type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token")
            GecmisBildirimler(navController = navController, token = token)
        }
        composable("WebGorunumu"){
            WebGorunumu(navController = navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun SayfaGecisleriPreview() {
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}