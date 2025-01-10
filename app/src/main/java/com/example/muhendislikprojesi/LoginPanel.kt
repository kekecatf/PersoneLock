package com.example.muhendislikprojesi

import android.annotation.SuppressLint
import android.content.Context

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.example.muhendislikprojesi.ui.theme.MuhendislikProjesiTheme
import com.example.muhendislikprojesi.ui.theme.ThemedLogo
import com.example.tokentry.retrofitt.ApiUtils
import com.example.tokentry.retrofitt.AuthService
import com.example.tokentry.retrofitt.LoginRequest
import com.example.tokentry.storage.getLoginInfo
import com.example.tokentry.storage.getThemePreference
import com.example.tokentry.storage.isRememberMeEnabled
import com.example.tokentry.storage.saveLoginInfo
import com.example.tokentry.storage.saveThemePreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPanel(navController: NavController) {
    val context = LocalContext.current
    val apiService = ApiUtils.getAuthService()

    // Klavye Davranışı İçin Değişkenler
    val focusManager = LocalFocusManager.current

    // Tema İçin Değişkenler
    val isSystemDarkTheme = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(getThemePreference(context) || isSystemDarkTheme) }

    // Veri Değişkenleri
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf<String?>(null) }
    var token by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    // SnackbarHostState for displaying Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Otomatik Bilgileri Doldurma Kısmı
    if (isRememberMeEnabled(context)) {
        val loginInfo = getLoginInfo(context)
        email = loginInfo.email
        password = loginInfo.password
        rememberMe = true
    }

    MuhendislikProjesiTheme(darkTheme = isDarkTheme) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            content = {

                // Dıştaki Büyük Box
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .background(colorScheme.background, shape = RoundedCornerShape(10.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        // Logo Kısmı
                        ThemedLogo(darkTheme = isDarkTheme, 200)

                        // E-Mail TextField Kısmı
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("E-Posta") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colorScheme.background,
                                unfocusedContainerColor = colorScheme.background,
                                disabledContainerColor = colorScheme.background,
                                cursorColor = colorScheme.onBackground,
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Email
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Şifre TextField Kısmı
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Şifre") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = colorScheme.background,
                                unfocusedContainerColor = colorScheme.background,
                                disabledContainerColor = colorScheme.background,
                                cursorColor = colorScheme.onBackground,
                            ),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Text
                            ),
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

                        Spacer(modifier = Modifier.height(16.dp))

                        // Beni Hatırla Butonu
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

                        // Tema Değiştirme Switchi
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = {
                                isDarkTheme = it
                                saveThemePreference(context, it)
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        // Giriş Butonu
                        Button(
                            onClick = {
                                val loginRequest = LoginRequest(email, password)
                                loginUser(context, loginRequest, apiService, rememberMe) { result, tokenValue ->
                                    loginResult = result
                                    token = tokenValue

                                    if (tokenValue != null) {
                                        navController.navigate("MainPanel/$tokenValue")
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = "Bilgiler Eksik Ya Da Yanlis")
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text("Giriş Yap")
                        }
                    }
                }
            }
        )
    }
}

////Kullanıcı Giriş Bilgilerini Post Eden Fonksiyon
//fun loginUser(
//    context: Context,
//    loginRequest: LoginRequest,
//    apiService: AuthService,
//    rememberMe: Boolean,
//    onResult: (String?, String?) -> Unit
//) {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val response = apiService.mobileLogin(loginRequest).execute()
//            if (response.isSuccessful) {
//                val loginResponse = response.body()
//                val token = loginResponse?.token
//                val message = loginResponse?.message
//                withContext(Dispatchers.Main) {
//                    if (message == "Giriş başarılı") {  // Success message
//                        if (rememberMe) {
//                            saveLoginInfo(context, loginRequest.email, loginRequest.password, true)
//                        } else {
//                            saveLoginInfo(context, "", "", false)
//                        }
//                        onResult("Giriş başarılı", token)
//                    } else {
//                        onResult("Giriş başarısız: $message", null)
//                    }
//                }
//            } else {
//                withContext(Dispatchers.Main) {
//                    onResult("Failed to login: ${response.code()}", null)
//                }
//            }
//        } catch (e: Exception) {
//            withContext(Dispatchers.Main) {
//                onResult("Error: ${e.message}", null)
//            }
//        }
//    }
//}

//Mock LoginUser fonksiyonu
fun loginUser(
    context: Context,
    loginRequest: LoginRequest,
    apiService: AuthService?, // Kullanılmıyor ama yapıyı koruyoruz
    rememberMe: Boolean,
    onResult: (String?, String?) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        delay(1000) // Gerçekçi bir gecikme simülasyonu (isteğe bağlı)

        withContext(Dispatchers.Main) {
            // Mock kullanıcı bilgileri
            val validEmail = "test@example.com"
            val validPassword = "password123"

            if (loginRequest.email == validEmail && loginRequest.password == validPassword) {
                val mockToken = "mock_token_123"
                if (rememberMe) {
                    saveLoginInfo(context, loginRequest.email, loginRequest.password, true)
                } else {
                    saveLoginInfo(context, "", "", false)
                }
                onResult("Giriş başarılı", mockToken)
            } else {
                onResult("Giriş başarısız: Bilgiler yanlış", null)
            }
        }
    }
}


@SuppressLint("NewApi")
@Preview
@Composable
fun LoginPanelPreview(){
    MuhendislikProjesiTheme {
        SayfaGecisleri()
    }
}
