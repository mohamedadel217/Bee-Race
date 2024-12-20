package com.example.feature.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import android.graphics.Color as AndroidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature.R

@Composable
fun WinnerScreen(winnerName: String, winnerColor: String, onRestart: () -> Unit) {
    val safeColor = try {
        Color(android.graphics.Color.parseColor(winnerColor))
    } catch (e: IllegalArgumentException) {
        MaterialTheme.colors.onBackground
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Winner Text
        Text(
            text = "Winner",
            style = MaterialTheme.typography.h4,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Winner Icon with Circle Background
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = safeColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bee_icon), // Replace with your bee icon asset
                contentDescription = "Winner Icon",
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Winner Rank and Name
        Text(
            text = "1st",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.secondary
        )

        Text(
            text = winnerName,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Restart Button
        Button(
            onClick = onRestart,
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Re-Start Bee Race",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WinnerScreenPreview() {
    WinnerScreen(winnerName = "BeeWare", winnerColor = "#FFCC00", onRestart = {})
}
