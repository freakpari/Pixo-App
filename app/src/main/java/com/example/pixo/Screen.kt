package com.example.pixo

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Notification : Screen("notification")
    object Account : Screen("account")
    object PostDetail : Screen("post_detail")
}