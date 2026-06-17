package com.example.pixo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostDetailScreen(onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(Color(0xFFC5CAE9))
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "❮", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "💙 630", color = Color(0xFF002244), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "💬 27", color = Color(0xFF002244), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "🔗", color = Color(0xFF002244), fontSize = 18.sp)
            }
            Text(text = "🔖", color = Color(0xFF005B94), fontSize = 22.sp)
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "Jennifer",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF005B94),
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Colorful Butterflies in forest.",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF002244)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "in my mind heaven is as colorful as this picture. ✨🦋",
                color = Color(0xFF665577),
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        Text(
            text = "More Similar",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF002244),
            modifier = Modifier.padding(start = 20.dp, top = 24.dp, bottom = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFFCC80))
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFB39DDB))
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}