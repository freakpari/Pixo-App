package com.example.pixo

sealed class Screen(val route: String, val title: String) {
    object Account : Screen("account", "Account")
    object Home : Screen("home", "Home")
    object Notification : Screen("notification", "Notification")
    object PostDetail : Screen("post_detail", "Post Detail")
}