package com.example.pixo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainAppStructure()
        }
    }
}

@Composable
fun MainAppStructure() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .background(Color(0xFF005B94)),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val menuItems = listOf(
                    Triple(Screen.Account, "Account", "Account"),
                    Triple(Screen.Home, "Home", "Home"),
                    Triple(Screen.Notification, "Notification", "Notification")
                )

                menuItems.forEach { (screen, _, title) ->
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            //startDestination = Screen.Home.route, //landing page
            startDestination = Screen.Notification.route, //notification page for test
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Home.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F0FA)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Home Grid Screen\n", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            composable(
                route = Screen.Account.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F0FA)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Account & Profile Screen", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            composable(
                route = Screen.Notification.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                NotificationScreen(onNotificationClick = {
                    navController.navigate(Screen.PostDetail.route)
                })
            }

            composable(
                route = Screen.PostDetail.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                PostDetailScreen(onBackClick = {
                    navController.popBackStack()
                })
            }
        }
    }
}