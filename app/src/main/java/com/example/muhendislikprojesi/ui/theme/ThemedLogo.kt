package com.example.muhendislikprojesi.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.muhendislikprojesi.R

//Temaya Bağlı Logo Değiştirme Fonksiyonu
@Composable
fun ThemedLogo(darkTheme: Boolean = isSystemInDarkTheme(), size:Int) {
    val size = size
    val logoRes = if (darkTheme) {
        R.drawable.logo2_night// Gece teması için logo (drawable-night/logo2.png)
    } else {
        R.drawable.logo2 // Günlük tema için logo (drawable/logo2.png)
    }

    Image(
        painter = painterResource(id = logoRes),
        contentDescription = null,
        modifier = Modifier.size(size.dp)
    )
}