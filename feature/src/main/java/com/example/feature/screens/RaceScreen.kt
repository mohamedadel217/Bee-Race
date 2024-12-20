package com.example.feature.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.models.Bee
import com.example.feature.R
import com.example.feature.models.RaceScreenState
import com.example.feature.ui.RaceViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun RaceScreen(
    viewModel: RaceViewModel = getViewModel(),
    navController: NavController
) {
    val raceScreenState by viewModel.raceScreenState.collectAsState()
    val countdownTimer = remember { mutableStateOf(0) } // Timer state

    // Update countdown when raceScreenState emits data
    LaunchedEffect(raceScreenState) {
        if (raceScreenState is RaceScreenState.Data) {
            val state = raceScreenState as RaceScreenState.Data
            countdownTimer.value = state.duration
        }
    }

    // Countdown timer logic
    LaunchedEffect(countdownTimer.value) {
        if (countdownTimer.value > 0) {
            delay(1000L) // Delay for 1 second
            countdownTimer.value -= 1
        } else if (countdownTimer.value == 0 && raceScreenState is RaceScreenState.Data) {
            val state = raceScreenState as RaceScreenState.Data
            val winnerName = state.bees.firstOrNull()?.name ?: "Unknown"
            val winnerColor = state.bees.firstOrNull()?.color ?: "#FFFFFF"
            navController.navigate("winner_screen/$winnerName/$winnerColor") {
                popUpTo("race_screen") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bee Race") },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                when (raceScreenState) {
                    is RaceScreenState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is RaceScreenState.Data -> {
                        val state = raceScreenState as RaceScreenState.Data
                        RaceContent(
                            countdownTimer = countdownTimer.value,
                            bees = state.bees
                        )
                    }

                    is RaceScreenState.Error -> {
                        val state = raceScreenState as RaceScreenState.Error
                        if (state.captchaUrl != null) {
                            RecaptchaScreen(
                                captchaUrl = state.captchaUrl,
                                onCaptchaSolved = { navController.popBackStack() } // Navigate back after solving captcha
                            )
                        } else {
                            ErrorScreen(
                                errorMessage = state.message ?: "Unknown error",
                                onRetry = { viewModel.observeRaceData() } // Retry fetching race data
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun RaceContent(countdownTimer: Int, bees: List<Bee>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Countdown Timer
        Text(
            text = String.format("Time remaining: %02d:%02d", countdownTimer / 60, countdownTimer % 60),
            style = MaterialTheme.typography.h4,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )

        // Bee List
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(bees) { index, bee ->
                BeeRow(
                    rank = index + 1,
                    bee = bee,
                    showMedal = index < 3
                )
                if (index != bees.size - 1) {
                    Divider()
                }
            }
        }
    }
}

@Composable
fun BeeRow(rank: Int, bee: Bee, showMedal: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color(android.graphics.Color.parseColor(bee.color)),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bee_icon), // Replace with your asset
                contentDescription = "Bee Icon"
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Rank and Bee Name
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "${rank.ordinalRank()} ${bee.name}", style = MaterialTheme.typography.body1)
        }

        // Medal (Only for Top 3 Bees)
        if (showMedal) {
            Image(
                painter = painterResource(
                    id = when (rank) {
                        1 -> R.drawable.gold_medal
                        2 -> R.drawable.silver_medal
                        3 -> R.drawable.bronze_medal
                        else -> 0
                    }
                ),
                contentDescription = "Medal Icon",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

fun Int.ordinalRank(): String {
    return when (this) {
        1 -> "1st"
        2 -> "2nd"
        3 -> "3rd"
        else -> "${this}th"
    }
}

