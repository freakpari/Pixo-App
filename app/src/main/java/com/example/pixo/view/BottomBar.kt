package com.example.pixo.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pixo.R
import com.example.pixo.ui.theme.*

enum class NavScreen { ACCOUNT, HOME, NOTIFICATION }

@Composable
fun BottomBar(selectedScreen: NavScreen, onTabSelected: (NavScreen) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Primary8
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PixoBottomNavItem(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_nav_account,
                label = "Account",
                isSelected = selectedScreen == NavScreen.ACCOUNT,
                onClick = { onTabSelected(NavScreen.ACCOUNT) }
            )
            PixoBottomNavItem(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_nav_home,
                label = "Home",
                isSelected = selectedScreen == NavScreen.HOME,
                onClick = { onTabSelected(NavScreen.HOME) }
            )
            PixoBottomNavItem(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.ic_nav_notif,
                label = "Notification",
                isSelected = selectedScreen == NavScreen.NOTIFICATION,
                onClick = { onTabSelected(NavScreen.NOTIFICATION) }
            )
        }
    }
}

@Composable
fun PixoBottomNavItem(
    modifier: Modifier,
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
            fontFamily = interFont
        )
    }
}