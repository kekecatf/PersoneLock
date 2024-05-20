package com.example.muhendislikprojesi

import android.annotation.SuppressLint

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.muhendislikprojesi.storage.clearLoginPreferences
import com.example.muhendislikprojesi.storage.getLoginPreferences
import com.example.muhendislikprojesi.storage.getThemePreference
import com.example.muhendislikprojesi.storage.saveLoginPreferences
import com.example.muhendislikprojesi.storage.saveThemePreference
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.retrofitdeneme6.retrofit.ApiResponse
import com.example.retrofitdeneme6.retrofit.ApiUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPanel(navController: NavController) {

    val context = LocalContext.current

    // Tema İçin Gerekli Değişkenler
    val isSystemDarkTheme = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(getThemePreference(context)) }

    // Snackbar İçin Gerekli Değişkenler
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Veriler Değişkenleri
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    // Enter İşlevi İçin Değişken
    val focusManager = LocalFocusManager.current

    // Uygulama başladığında Kayıtlı Verileri Al
    LaunchedEffect(Unit) {
        val loginPreferences = getLoginPreferences(context)
        val savedRememberMe = loginPreferences["rememberMe"] as Boolean
        if (savedRememberMe) {
            userEmail = loginPreferences["username"] as String
            userPassword = loginPreferences["password"] as String
            rememberMe = savedRememberMe
            navController.navigate("MainPanel") // Otomatik giriş yap
        }
    }

    MuhendislikProjesiTheme(darkTheme = isDarkTheme) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            content = {
                Surface(color = colorScheme.primaryContainer) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Logonun Olduğu Kısım
                        Image(
                            painter = painterResource(id = R.drawable.logo2),
                            contentDescription = "", modifier = Modifier.size(200.dp)
                        )
                        // Tema Switchi Olduğu Kısım
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = {
                                isDarkTheme = it
                                saveThemePreference(context, isDarkTheme)
                            }
                        )

                        Column {
                            // Kullanıcı Adı TextField Olduğu Kısım
                            TextField(
                                value = userEmail,
                                onValueChange = { userEmail = it },
                                label = { Text(text = "Kullanıcı Adı") },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    cursorColor = colorScheme.onBackground
                                ),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Email
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                )
                            )
                        }

                        Column {
                            // Şifre TextField Olduğu Kısım
                            TextField(
                                value = userPassword,
                                onValueChange = { userPassword = it },
                                label = { Text(text = "Sifre") },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = colorScheme.background,
                                    cursorColor = colorScheme.onBackground
                                ),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Text
                                ),
                                keyboardActions = KeyboardActions(
                                    // Enter'a basıldığında otomatil giriş yaptığı kısım
                                    onDone = {
                                        focusManager.clearFocus()
                                        CoroutineScope(Dispatchers.Main).launch {
                                            val success = postVeri(userEmail, userPassword)
                                            if (success) {
                                                if (rememberMe) {
                                                    saveLoginPreferences(context, userEmail, userPassword, rememberMe)
                                                } else {
                                                    clearLoginPreferences(context)
                                                }
                                                navController.navigate("MainPanel")
                                            } else {
                                                scope.launch {
                                                    snackbarHostState.showSnackbar(message = "Bilgiler Eksik Ya Da Yanlis")
                                                }
                                            }
                                        }
                                    }
                                ),
                                // Şifre Görünürlüğü Olduğu Kısım
                                trailingIcon = {
                                    val image = if (passwordVisible)
                                        painterResource(id = R.drawable.ic_visibility)
                                    else painterResource(id = R.drawable.ic_visibility_off)

                                    IconButton(onClick = {
                                        passwordVisible = !passwordVisible
                                    }) {
                                        Icon(painter = image, contentDescription = null)
                                    }
                                }
                            )
                        }
                        // "Beni Hatırla" Checkbox'ı
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it }
                            )
                            Text(text = "Beni Hatırla")
                        }
                        // Giriş Butonu Olduğu Kısım
                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    val success = postVeri(userEmail, userPassword)
                                    if (success) {
                                        if (rememberMe) {
                                            saveLoginPreferences(context, userEmail, userPassword, rememberMe)
                                        } else {
                                            clearLoginPreferences(context)
                                        }
                                        navController.navigate("MainPanel")
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = "Bilgiler Eksik Ya Da Yanlis")
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.background,
                                contentColor = colorScheme.onBackground
                            ),
                            modifier = Modifier.size(250.dp, 50.dp)
                        ) {
                            Text(text = "Giris Yap", color = colorScheme.primary)
                        }
                    }
                }
            }
        )
    }
}

//RETROFİT KISMI
//Post İşlemi
private suspend fun postVeri(email: String, password: String): Boolean {
    return suspendCoroutine { continuation ->
        val kisilerDaoInterface = ApiUtils.getVerilerDaoInterface()

        val json = JSONObject().apply {
            put("email", email)
            put("password", password)
        }

        val requestBody = RequestBody.create("application/json".toMediaType(), json.toString())

        val call = kisilerDaoInterface.addVeri(requestBody)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val yeniVeri = response.body()
                    val message = yeniVeri?.message
                    if (message == "Giriş başarılı") {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                } else {
                    Log.e("Post İsteği", "Hata: ${response.code()}")
                    continuation.resume(false)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Post İsteği", "İstek başarısız: ${t.message}")
                continuation.resume(false)
            }
        })
    }
}

@Preview
@Composable
fun LoginPanelPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}
