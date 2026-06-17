package com.example.pixo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.example.pixo.R
import com.example.pixo.data.local.NotificationEntity
import com.example.pixo.ui.theme.*
import com.example.pixo.ui.theme.findActivity
import com.example.pixo.viewmodel.NotificationViewModel
import java.text.SimpleDateFormat
import java.util.*

fun formatRelativeTime(dateString: String): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = format.parse(dateString) ?: return dateString
        val diff = Date().time - date.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        when {
            minutes < 1 -> "now"
            minutes < 60 -> "${minutes}m ago"
            hours < 24 -> "${hours}h ago"
            else -> "${days}d ago"
        }
    } catch (e: Exception) {
        try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = format.parse(dateString) ?: return dateString
            val diff = Date().time - date.time
            val hours = diff / (1000 * 60 * 60)
            val days = hours / 24
            if (hours < 24) "${hours}h ago" else "${days}d ago"
        } catch (ex: Exception) {
            dateString.take(10)
        }
    }
}

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel,
    onNotificationClick: () -> Unit = {}
) {
    val notifications by viewModel.notifications.collectAsState()

    val context = LocalContext.current
    val view = LocalView.current
    LaunchedEffect(Unit) {
        val activity = context.findActivity()
        activity?.window?.let { window ->
            window.statusBarColor = Primary8.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(Primary8)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${notifications.size} new notifications!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = interFont,
                color = Primary8
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { }
            ) {
                Text(
                    text = "Read all",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = interFont
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_double),
                    contentDescription = null,
                    tint = Primary8,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        if (notifications.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Primary8)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(notifications) { item ->
                    NotificationRow(item, onNotificationClick)
                    HorizontalDivider(color = Color(0xFFF1F1F1), thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun NotificationRow(item: NotificationEntity, onClick: () -> Unit) {
    val badgeIcon = when {
        item.preview.contains("Liked", ignoreCase = true) -> R.drawable.ic_badge_heart
        item.preview.contains("Commented", ignoreCase = true) -> R.drawable.ic_badge_comment
        item.preview.contains("following", ignoreCase = true) -> R.drawable.ic_badge_star
        else -> R.drawable.ic_badge_plus
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(54.dp)) {
            AsyncImage(
                model = item.profileImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomStart)
            )
            Image(
                painter = painterResource(id = badgeIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.TopEnd)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.senderName,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                fontFamily = interFont,
                color = Primary8
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${item.preview} • ${formatRelativeTime(item.date)}",
                fontSize = 13.sp,
                color = Color.Gray,
                fontFamily = interFont,
                maxLines = 2
            )
        }
    }
}