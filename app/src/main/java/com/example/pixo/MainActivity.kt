package com.example.pixo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pixo.data.local.AppDatabase
import com.example.pixo.data.remote.RetrofitClient
import com.example.pixo.data.repository.AppRepository
import com.example.pixo.ui.theme.PixoTheme
import com.example.pixo.ui.theme.Primary8
import com.example.pixo.view.*
import com.example.pixo.viewmodel.AuthViewModel
import com.example.pixo.viewmodel.AuthViewModelFactory
import com.example.pixo.viewmodel.NotificationViewModel
import com.example.pixo.viewmodel.NotificationViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Primary8.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Primary8.toArgb())
        )

        val database = AppDatabase.getDatabase(applicationContext)
        val apiService = RetrofitClient.instance
        val repository = AppRepository(apiService, database.notificationDao(), database.postDao())

        val authViewModel = ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        val notificationViewModel = ViewModelProvider(this, NotificationViewModelFactory(repository))[NotificationViewModel::class.java]

        setContent {
            PixoTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                var showAccountBottomSheet by remember { mutableStateOf(false) }

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
                                    if (selectedTab == NavScreen.ACCOUNT) {
                                        showAccountBottomSheet = true
                                    } else {
                                        val destination = when (selectedTab) {
                                            NavScreen.HOME -> Screen.Home.route
                                            NavScreen.NOTIFICATION -> Screen.Notification.route
                                            else -> Screen.Home.route
                                        }
                                        if (currentRoute != destination) {
                                            navController.navigate(destination) {
                                                popUpTo(Screen.Home.route) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
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
                                HomeScreen(
                                    onPostClick = {
                                        navController.navigate(Screen.PostDetail.route)
                                    }
                                )
                            }

                            composable(Screen.Notification.route) {
                                NotificationScreen(
                                    viewModel = notificationViewModel
                                )
                            }

                            composable(Screen.PostDetail.route) {
                                PostDetailScreen(onBackClick = {
                                    navController.popBackStack()
                                })
                            }
                        }

                        if (showAccountBottomSheet) {
                            ModalBottomSheet(
                                onDismissRequest = { showAccountBottomSheet = false },
                                sheetState = rememberModalBottomSheetState(),
                                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                                containerColor = Color.White
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .background(Color.White),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Account Details (Bottom Sheet)",
                                        color = Primary8,
                                        fontSize = 20.sp,
                                        fontFamily = interFont
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}