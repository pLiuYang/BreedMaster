package com.breedmaster.ui.breedchallenge

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.breedmaster.ui.theme.BreedMasterTheme

@Composable
fun BreedChallengeScreen(modifier: Modifier = Modifier.Companion) {
    Column(modifier.fillMaxSize().padding(top = 32.dp)) {
        AsyncImage(
            model = "https://images.dog.ceo/breeds/hound-afghan/n02088094_890.jpg",
            contentDescription = "Picture of a dog",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .size(242.dp),
        )

        Text(
            text = "Please choose the breed of this dog",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )

        val onBreedOptionClick: (Int) -> Unit =
            { index -> Log.d("BreedChallengeScreen", "Click option $index") }
        BreedOption("Option 1") { onBreedOptionClick(0) }
        BreedOption("Option 2") { onBreedOptionClick(1) }
        BreedOption("Option 3") { onBreedOptionClick(2) }
    }
}

@Composable
fun BreedOption(
    textContent: String,
    modifier: Modifier = Modifier.Companion,
    onBreedOptionClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onBreedOptionClick
    ) {
        Text(textContent)
    }
}

@Preview(showBackground = true)
@Composable
fun BreedChallengeScreenPreview() {
    BreedMasterTheme {
        BreedChallengeScreen()
    }
}
