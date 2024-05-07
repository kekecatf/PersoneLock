package com.example.muhendislikprojesi

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme

@Preview
@Composable
fun MainPanelPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPanel(navController: NavController){

    val activity =(LocalContext.current as Activity )

    //Geri Tuşu İşlevi
    BackHandler (onBack = {
        activity.finish()
    })

    val switchDurum = remember {
        mutableStateOf(false)
    }
    
    //Logo ve Kullanıcı Bilgilerinin Olduğu Kısım
    Column (modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.Tenrengi)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)){
            Card ( modifier = Modifier
                .weight(40f)
                .height(200.dp)
                .padding(end = 20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.KoyuMavi))
            ){
                Image(painter = painterResource(id = R.drawable.biyometrik), contentDescription = "",Modifier.padding(top = 20.dp))
            }
            Card ( modifier = Modifier
                .weight(60f)
                .height(200.dp),colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.KoyuMavi)
            )) {
                Text(text = "KSÜ BM",
                    color = colorResource(id = R.color.KahveRengi),
                    fontSize = 18.sp)
                Text(text = "Takip Sistemine",
                    color = colorResource(id = R.color.Tenrengi),
                    fontSize = 18.sp)
                Text(text = "Hoş Geldiniz",
                    color = colorResource(id = R.color.KahveRengi),
                    fontSize = 18.sp)
                Text(text = "Atıf Kekeç",
                    color = colorResource(id = R.color.Tenrengi),
                    fontSize = 18.sp)
                Text(text = "En Son Giriş Tarihi:",
                    color = colorResource(id = R.color.KahveRengi),
                    fontSize = 18.sp)
                Text(text = "02.07.2002 ",
                    color = colorResource(id = R.color.Tenrengi),
                    fontSize = 18.sp)
                Text(text = "En Son Giriş Saati:",
                    color = colorResource(id = R.color.KahveRengi),
                    fontSize = 18.sp)
                Text(text = "16:07",
                    color = colorResource(id = R.color.Tenrengi),
                    fontSize = 18.sp)

            }

        }

        //Dururuların Olduğu Kısım
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .size(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.AcikMavi),
            contentColor = colorResource(id = R.color.Tenrengi))){
        Row (modifier = Modifier
            .fillMaxSize()
            .clickable { navController.navigate("Duyurular") },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            Text(text = "DUYURULAR --->", modifier = Modifier.padding(start = 25.dp))
            }
        }



        //Geçmiş Uyarıların Olduğu Kısım
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .size(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.AcikMavi),
                contentColor = colorResource(id = R.color.Tenrengi))){
            Row (modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate("GecmisUyarilar") },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Text(text = "GEÇMİŞ UYARILAR --->", modifier = Modifier.padding(start = 25.dp))
            }
        }
        
        //Kayıtlı Cihazların Olduğu Kısım
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .size(100.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.AcikMavi),
                contentColor = colorResource(id = R.color.Tenrengi))){
            Row (modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate("KayitliCihazlar") },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Text(text = "KAYITLI CİHAZLAR --->", modifier = Modifier.padding(start = 25.dp))
            }
        }

        //Login Ekranına Dönme Butonu
        Button(onClick = { navController.navigate("LoginPanel") }) {
            Text(text = "Login Ekranı")
        }

        //Uygulamayı Kapatma Butonu
        Button(onClick = { activity.finish() }) {
            Text(text = "Çıkış")
        }
        
    }

}
