package com.example.muhendislikprojesi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.R

@Composable
fun MainPanel(navController: NavController){

    val switchDurum = remember {
        mutableStateOf(false)
    }
    
    //Logo ve Kullanıcı Bilgilerinin Olduğu kısım
    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally){
        Row {
            Card ( modifier = Modifier.weight(50f)){
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "")
            }
            Card ( modifier = Modifier.weight(50f)) {
                Text(text = "KSÜ Bilgisayar Mühendisliği " +
                        "Takip Sistemine")
                Text(text = "Hoş Geldiniz" +
                        "Atıf Kekeç")
                Text(text = "En Son giriş tarihi" +
                        "02.07.2002")
                Text(text = "En son giriş saati" +
                        "16:35")

            }

        }
       
        //Ek Mesai Tercih Kartının Olduğu Kısım
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .size(100.dp)){
            Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Text(text = "EK MESAİ TERCİH DURUMU")
                Switch(
                    checked = switchDurum.value,
                    onCheckedChange = {
                        switchDurum.value=it
                    }, modifier = Modifier.padding(start = 25.dp))
            }
        }
        
        //Dururuların Olduğu Kısım
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .size(100.dp)){
            Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Text(text = "DUYURULAR --->", modifier = Modifier.padding(start = 25.dp))
            }
        }
        
        //Kayıtlı Cihazların Olduğu Kısım
        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .size(100.dp)){
            Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Text(text = "KAYITLI CİHAZLAR", modifier = Modifier.padding(start = 25.dp))
            }
        }
        Button(onClick = { navController.navigate("LoginPanel") }) {
            Text(text = "Login Ekranı")
        }
        
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Çıkış")
        }
        
    }

}
