package com.example.pixo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pixo.R
import com.example.pixo.ui.theme.*

val forteFont = FontFamily(Font(R.font.forte))
val interFont = FontFamily(
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

@Composable
fun LandingScreen(onLoginClick: () -> Unit, onSignupClick: () -> Unit) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Primary7, Secondary4)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(116.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = "Pixo",
                color = White,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = forteFont
            )

            Spacer(modifier = Modifier.height(144.dp))

            Text(
                text = "Welcome!",
                color = White,
                fontSize = 32.sp,
                fontFamily = interFont,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary1),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Login",
                    color = Primary6,
                    fontSize = 14.sp,
                    fontFamily = interFont,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSignupClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary1),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Primary6,
                    fontSize = 14.sp,
                    fontFamily = interFont,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}