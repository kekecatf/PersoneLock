package com.example.muhendislikprojesi

import android.annotation.SuppressLint

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.Retrofit.ApiUtils
import com.example.muhendislikprojesi.Retrofit.Veriler
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Preview
@Composable
fun LoginPanelPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPanel(navController: NavController) {

    //retrofit kısmı
    LaunchedEffect(key1 = true) {
        tumVeriler()
        Log.e("deneme","123")

    }



    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        content ={
            Surface (color = colorResource(id = R.color.Tenrengi)){

                val tfKullaniciAdi = remember { mutableStateOf("") }
                val tfSifre = remember { mutableStateOf("") }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //Logonun Olduğu Kısım
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "", modifier = Modifier.size(200.dp))

                    //Kullanıcı Adı Text Field Olduğu Kısım
                    Column {
                        val focusManager= LocalFocusManager.current
                        Text(text = "Kullanıcı Adı Giriniz: admin")
                        TextField(
                            value = tfKullaniciAdi.value,
                            onValueChange = { tfKullaniciAdi.value = it },
                            label = { Text(text = "Kullanıcı Adı") },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = colorResource(id = R.color.KahveRengi),
                                cursorColor = colorResource(id = R.color.KoyuMavi)
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Text),
                            keyboardActions = KeyboardActions(
                                onNext = {focusManager.moveFocus(FocusDirection.Down)}
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
                                containerColor = colorResource(id = R.color.KahveRengi)
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),

                            )
                    }

                    //Giriş Butonu Olduğu Kısım
                    Button(onClick = { Log.e("Buton ","Giris yapildi")
                        if (tfKullaniciAdi.value=="admin"&&tfSifre.value=="0000"){
                            navController.navigate("MainPanel")
                            scope.launch {
                                snackbarHostState.showSnackbar(message = "Giris Yapildi")
                            }
                        }
                        else{
                            scope.launch {
                                snackbarHostState.showSnackbar(message = "Bilgiler Eksik Ya Da Yanlis")
                        }}

                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.KoyuMavi),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(250.dp,50.dp)

                    ) {
                        Text(text = "Giris Yap")
                    }
                }
            }
        }
    )

}

//retrofit kısmı
fun tumVeriler(){
    val kisilerDaoInterface = ApiUtils.getVerilerDaoInterface()

    kisilerDaoInterface.tumVeriler().enqueue(object : Callback<Veriler> {
        override fun onResponse(call: Call<Veriler>, response: Response<Veriler>) {
            val sonuc = response.body()?.firstName

            Log.e("sonuc", sonuc!!)
        }

        override fun onFailure(call: Call<Veriler>, t: Throwable) {

        }
    })
}
