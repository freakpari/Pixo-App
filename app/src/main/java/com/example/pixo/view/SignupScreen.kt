package com.example.pixo.view

import android.widget.Toast
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
import com.example.pixo.R
import com.example.pixo.data.remote.SignupRequest
import com.example.pixo.ui.theme.*
import com.example.pixo.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    onBackClick: () -> Unit,
    onSignupSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val gradient = Brush.verticalGradient(colors = listOf(Primary7, Secondary4))

    LaunchedEffect(Unit) {
        authViewModel.signupResult.collectLatest { response ->
            isLoading = false
            if (response != null) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show()
                    onSignupSuccess()
                } else {
                    Toast.makeText(context, "Signup Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.errorFlow.collectLatest { error ->
            isLoading = false
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

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
                            onClick = onBackClick,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(30.dp)
                                .border(1.dp, Primary1, RoundedCornerShape(8.dp))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = null,
                                tint = Primary7,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Text(text = "Sign Up", modifier = Modifier.align(Alignment.Center), fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = interFont, color = Primary7)
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email", fontFamily = interFont) },
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_email), contentDescription = null, modifier = Modifier.size(20.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = myTextFieldColors,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Password", fontFamily = interFont) },
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_key), contentDescription = null, modifier = Modifier.size(20.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = myTextFieldColors,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = confirmpassword,
                        onValueChange = { confirmpassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Confirm Password", fontFamily = interFont) },
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_key), contentDescription = null, modifier = Modifier.size(20.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = myTextFieldColors,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()
                            val passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$".toRegex()

                            if (email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
                                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            } else if (!email.matches(emailRegex)) {
                                Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                            } else if (!password.matches(passwordRegex)) {
                                Toast.makeText(context, "Password must be at least 6 characters and contain letters and numbers", Toast.LENGTH_SHORT).show()
                            } else if (password != confirmpassword) {
                                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                            } else {
                                isLoading = true
                                authViewModel.signup(SignupRequest(email, password))
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary6),
                        enabled = !isLoading
                    ) {
                        if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        else Text("Sign Up", fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = interFont)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                        Text(text = " or ", modifier = Modifier.padding(horizontal = 8.dp), color = Color.Gray, fontSize = 12.sp)
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    SocialButton(iconRes = R.drawable.ic_google, text = "Continue with Google")
                    Spacer(modifier = Modifier.height(12.dp))
                    SocialButton(iconRes = R.drawable.ic_facebook, text = "Continue with Facebook")

                    Spacer(modifier = Modifier.weight(1f))

                    Row {
                        Text(text = "Already have an account? ", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Login", modifier = Modifier.padding(start = 4.dp), fontSize = 12.sp, color = Primary6, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}