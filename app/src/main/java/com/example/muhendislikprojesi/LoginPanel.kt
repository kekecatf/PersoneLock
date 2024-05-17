package com.example.muhendislikprojesi

import android.annotation.SuppressLint

import android.util.Log
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
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.retrofitdeneme6.retrofit.ApiResponse
import com.example.retrofitdeneme6.retrofit.ApiUtils
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPanel(navController: NavController) {

    LaunchedEffect(Unit) {
        val email = "atifkekec@personelock.com"
        val password = "123456789hjK*"

        // addVeri fonksiyonunu çağırarak POST isteği gönder
        postVeri(email, password)
    }

    //Scankbar(Alttan gelen bildirim) Oluşturma Kısmı
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
                        painter = painterResource(id = R.drawable.logo2),
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
//RETROFİT KISMI
//Post İşlemi
private fun postVeri(email: String, password: String) {
    val kisilerDaoInterface = ApiUtils.getVerilerDaoInterface()

    val json = JSONObject()
    json.put("email", email)
    json.put("password", password)

    val requestBody = RequestBody.create("application/json".toMediaType(), json.toString())

    val call = kisilerDaoInterface.addVeri(requestBody)
    call.enqueue(object : Callback<ApiResponse> {
        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
            if (response.isSuccessful) {
                val yeniVeri = response.body()
                val message = yeniVeri?.message
                // message değişkeni sunucudan dönen mesajı içerir
                Log.d("Post İsteği", "Başarılı: $message")
            } else {
                // Sunucudan hata dönmesi durumu
                Log.e("Post İsteği", "Hata: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
            // İstek başarısız olduğunda
            Log.e("basarisiz","hata")
        }
    })
}

@Preview
@Composable
fun LoginPanelPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri { departmentID, firstName, id, email, userName, emailConfirmed,securityStamp ->
            Log.d("MainActivity", "departmentID: $departmentID, firstName: $firstName, id: $id, email: $email, userName: $userName, emailConfirmed: $emailConfirmed")}
    }
}
