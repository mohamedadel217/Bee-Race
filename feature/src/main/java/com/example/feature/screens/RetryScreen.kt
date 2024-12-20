package com.example.feature.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RetryScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Retry") }) },
        content = { contentPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { navController.navigate("race_screen") }) {
                    Text(text = "Retry")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RetryScreenPreview() {
    RetryScreen(navController = rememberNavController())
}
