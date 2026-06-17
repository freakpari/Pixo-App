package com.example.pixo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pixo.data.local.AppDatabase
import com.example.pixo.data.remote.RetrofitClient
import com.example.pixo.data.repository.AppRepository
import com.example.pixo.ui.theme.PixoTheme
import com.example.pixo.view.*
import com.example.pixo.viewmodel.AuthViewModel
import com.example.pixo.viewmodel.AuthViewModelFactory
import com.example.pixo.viewmodel.NotificationViewModel
import com.example.pixo.viewmodel.NotificationViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val apiService = RetrofitClient.instance
        val repository = AppRepository(apiService, database.notificationDao())

        val authViewModel = ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        val notificationViewModel = ViewModelProvider(this, NotificationViewModelFactory(repository))[NotificationViewModel::class.java]

        setContent {
            PixoTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        val isBottomBarVisible = currentRoute == Screen.Home.route ||
                                currentRoute == Screen.Notification.route ||
                                currentRoute == Screen.Account.route

                        if (isBottomBarVisible) {
                            val selectedScreen = when (currentRoute) {
                                Screen.Home.route -> NavScreen.HOME
                                Screen.Account.route -> NavScreen.ACCOUNT
                                else -> NavScreen.NOTIFICATION
                            }
                            BottomBar(
                                selectedScreen = selectedScreen,
                                onTabSelected = { selectedTab ->
                                    val destination = when (selectedTab) {
                                        NavScreen.HOME -> Screen.Home.route
                                        NavScreen.ACCOUNT -> Screen.Account.route
                                        NavScreen.NOTIFICATION -> Screen.Notification.route
                                    }
                                    if (currentRoute != destination) {
                                        navController.navigate(destination) {
                                            popUpTo(Screen.Home.route) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Landing.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Landing.route) {
                            LandingScreen(
                                onLoginClick = { navController.navigate(Screen.Login.route) },
                                onSignupClick = { navController.navigate(Screen.Signup.route) }
                            )
                        }

                        composable(Screen.Login.route) {
                            LoginScreen(
                                authViewModel = authViewModel,
                                onBackClick = { navController.popBackStack() },
                                onLoginSuccess = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Landing.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Signup.route) {
                            SignupScreen(
                                authViewModel = authViewModel,
                                onBackClick = { navController.popBackStack() },
                                onSignupSuccess = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Landing.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Home.route) {
                            HomeScreen()
                        }

                        composable(Screen.Notification.route) {
                            NotificationScreen(
                                viewModel = notificationViewModel,
                                onNotificationClick = {
                                    navController.navigate(Screen.PostDetail.route)
                                }
                            )
                        }

                        composable(Screen.Account.route) {
                            AccountScreen()
                        }

                        composable(Screen.PostDetail.route) {
                            PostDetailScreen(onBackClick = {
                                navController.popBackStack()
                            })
                        }
                    }
                }
            }
        }
    }
}