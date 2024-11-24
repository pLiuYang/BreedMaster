package com.breedmaster.ui.breedchallenge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.breedmaster.ui.theme.BreedMasterTheme

/**
 * Breed Challenge main screen
 */
@Composable
fun BreedChallengeScreen(
    viewModel: BreedChallengeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is BreedChallengeUiState.Loading -> {
                // Show loading indicator when loading
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is BreedChallengeUiState.BreedChallenge -> {
                BreedChallengeMain(
                    uiState as BreedChallengeUiState.BreedChallenge,
                    { option -> viewModel.answer(option) }
                )

                RefreshButton(
                    refreshAction = { viewModel.refreshQuestion() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }

            is BreedChallengeUiState.Error -> {
                Text(
                    text = "Something went wrong, please try again later",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(48.dp)
                        .align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge
                )

                RefreshButton(
                    refreshAction = { viewModel.refreshQuestion() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun RefreshButton(refreshAction: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = refreshAction,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Refresh"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BreedChallengeScreenPreview() {
    BreedMasterTheme {
        BreedChallengeScreen(BreedChallengeViewModel())
    }
}
