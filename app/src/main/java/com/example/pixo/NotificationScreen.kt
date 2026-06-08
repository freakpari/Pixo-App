package com.example.pixo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NotificationItem(
    val name: String,
    val action: String,
    val time: String,
    val badgeColor: Color,
    val badgeEmoji: String
)

@Composable
fun NotificationScreen(onNotificationClick: () -> Unit) {
    val notifications = listOf(
        NotificationItem("James", "sent you a friend request!", "43m ago", Color(0xFF8A33FF), "⭐"),
        NotificationItem("Mary", "commented on your photo.", "2h ago", Color(0xFF8A33FF), "💬"),
        NotificationItem("John", "sent you a friend request!", "6h ago", Color(0xFF8A33FF), "⭐"),
        NotificationItem("Robert", "liked your photo.", "19h ago", Color(0xFF8A33FF), "❤️"),
        NotificationItem("Jennifer", "added 2 photos.", "1d ago", Color(0xFF8A33FF), "➕")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0FA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "5 new notifications!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366)
            )
            Text(
                text = "Read all ✓✓",
                fontSize = 14.sp,
                color = Color(0xFF005B94),
                fontWeight = FontWeight.Bold
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notifications) { item ->
                NotificationRow(item = item, onClick = onNotificationClick)
            }
        }
    }
}

@Composable
fun NotificationRow(item: NotificationItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(56.dp)) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .background(Color(0xFFD9D9D9))
                )
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(item.badgeColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.badgeEmoji, fontSize = 10.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF002244)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.action,
                        fontSize = 13.sp,
                        color = Color(0xFF4A3B5C),
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.time,
                        fontSize = 11.sp,
                        color = Color(0xFF9A8CA8)
                    )
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(start = 92.dp),
            color = Color(0xFFE2D9EC),
            thickness = 0.5.dp
        )
    }
}