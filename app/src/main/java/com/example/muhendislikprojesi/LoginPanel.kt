package com.example.muhendislikprojesi

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPanel(navController: NavController) {
    Surface (color = colorResource(id = R.color.Gri)){

        val tfKullaniciAdi = remember {
            mutableStateOf("")
        }
        val tfSifre = remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Logonun Olduğu Kısım
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "", modifier = Modifier.size(180.dp))

            //Kullanıcı Adı Text Field Olduğu Kısım
            Column {
                Text(text = "Kullanıcı Adı Giriniz: admin")
                TextField(
                    value = tfKullaniciAdi.value,
                    onValueChange = { tfKullaniciAdi.value = it },
                    label = { Text(text = "Kullanıcı Adı") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White
                    )
                )
            }

            //Şifre Text Field Olduğu Kısım
            Column {
                Text(text = "Şifre Giriniz: 0000")
                TextField(
                    value = tfSifre.value,
                    onValueChange = { tfSifre.value = it },
                    label = { Text(text = "Sifre") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White
                    )
                )
            }

            //Giriş Butonu Olduğu Kısım
            Button(onClick = { Log.e("Buton ","Giris yapildi")
                if (tfKullaniciAdi.value=="admin"&&tfSifre.value=="0000"){
                    navController.navigate("MainPanel")
                }

                             },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                ),
                modifier = Modifier.size(250.dp,50.dp)

            ) {
                Text(text = "Giris Yap")
            }
        }
    }
}

