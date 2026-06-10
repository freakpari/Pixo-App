package com.example.pixo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pixo.ui.theme.*
import kotlinx.coroutines.launch
import android.app.Activity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixoTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val gradient = Brush.verticalGradient(colors = listOf(Primary7, Secondary4))

    val myTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Primary8,
        unfocusedTextColor = Primary8,
        focusedBorderColor = Primary8,
        unfocusedBorderColor = Primary8,
        focusedLeadingIconColor = Primary8,
        unfocusedLeadingIconColor = Primary8,
        focusedLabelColor = Primary8,
        unfocusedLabelColor = Primary8,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )

    Box(modifier = Modifier.fillMaxSize().background(gradient)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.padding(top = 40.dp, bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.size(60.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Pixo", color = Color.White, fontSize = 28.sp, fontFamily = forteFont)
            }

            Surface(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp).padding(bottom = 60.dp),
                shape = RoundedCornerShape(32.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { (context as? Activity)?.finish() },
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(26.dp)
                                .border(1.dp, Primary2, RoundedCornerShape(8.dp))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = null,
                                tint = Primary9,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Text(text = "Login", modifier = Modifier.align(Alignment.Center), fontSize = 32.sp, fontWeight = FontWeight.Bold, fontFamily = interFont, color = Primary7)
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email", fontFamily = interFont) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = myTextFieldColors

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Password", fontFamily = interFont) },
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_key),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = myTextFieldColors

                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                isLoading = true
                                scope.launch {
                                    try {
                                        val response = RetrofitClient.instance.loginUser(LoginRequest(email, password))
                                        isLoading = false
                                        if (response.isSuccessful) {
                                            Toast.makeText(context, "Success! Token: ${response.body()?.token}", Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(context, "Login Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        isLoading = false
                                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary6),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = interFont)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )

                        Text(
                            text = "or",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontFamily = interFont
                        )

                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    SocialButton(iconRes = R.drawable.ic_google, text = "Continue with Google")
                    Spacer(modifier = Modifier.height(12.dp))
                    SocialButton(iconRes = R.drawable.ic_facebook, text = "Continue with Facebook")

                    Spacer(modifier = Modifier.weight(1f))

                    Row {
                        Text(text = "Don't have an account? ", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Sign Up", fontSize = 12.sp, color = Primary6, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun SocialButton(iconRes: Int, text: String) {
    OutlinedButton(
        onClick = { },
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Primary6)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Primary6, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, fontFamily = interFont)
        }
    }
}