package com.example.feature.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature.screens.RaceScreen
import com.example.feature.screens.RecaptchaScreen
import com.example.feature.screens.RetryScreen
import com.example.feature.screens.StartScreen
import com.example.feature.screens.WinnerScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "start_screen") {
                composable("start_screen") { StartScreen(navController) }
                composable("race_screen") { RaceScreen(navController = navController) }
                composable("winner_screen/{winnerName}/{winnerColor}") { backStackEntry ->
                    val winnerName = backStackEntry.arguments?.getString("winnerName") ?: "Unknown"
                    val winnerColor =
                        backStackEntry.arguments?.getString("winnerColor") ?: "#FFFFFF"
                    WinnerScreen(
                        winnerName,
                        winnerColor,
                        onRestart = { navController.navigate("race_screen") })
                }
                composable("captcha_screen/{captchaUrl}") { backStackEntry ->
                    val captchaUrl = backStackEntry.arguments?.getString("captchaUrl") ?: ""
                    RecaptchaScreen(captchaUrl) { navController.popBackStack() }
                }
                composable("retry_screen") { RetryScreen(navController) }
            }
        }
    }
}