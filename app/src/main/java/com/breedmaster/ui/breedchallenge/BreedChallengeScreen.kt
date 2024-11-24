package com.breedmaster.ui.breedchallenge

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
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
                val state = uiState as BreedChallengeUiState.BreedChallenge
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp)
                ) {
                    // Question
                    AsyncImage(
                        model = state.question.imageUrl,
                        contentDescription = "Picture of a dog",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .size(300.dp),
                    )
                    Text(
                        text = "What breed is this adorable dog?",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )

                    // Options
                    // TODO check for edge case
                    for (i in 0 until 3) {
                        BreedOption(
                            state.question.breedOptions[i],
                            state.question.breed,
                            state.answer
                        ) { viewModel.answer(state.question.breedOptions[i]) }
                    }

                    // Result
                    Text(
                        text = getResultText(state.answer.state, state.question.breed),
                        modifier = Modifier
                            .padding(24.dp)
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                RefreshButton(
                    refreshAction = { viewModel.refreshQuestion() },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }

            is BreedChallengeUiState.Error -> {
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

/**
 * Buttons for the user to select an answer
 */
@Composable
fun BreedOption(
    breedOption: String,
    correctBreed: String,
    answer: BreedChallengeAnswer,
    modifier: Modifier = Modifier,
    onBreedOptionClick: () -> Unit,
) {
    Button(
        onClick = onBreedOptionClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = getOptionBgColor(
                breedOption,
                correctBreed,
                answer,
                MaterialTheme.colorScheme.primary
            ),
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(text = breedOption)
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

/**
 * Get the background color to use for breed options
 */
@VisibleForTesting
fun getOptionBgColor(
    breedOption: String,
    correctBreed: String,
    answer: BreedChallengeAnswer,
    primary: Color
): Color {
    return if (answer.state == AnswerState.ANSWERING || breedOption != answer.breed) {
        primary
    } else if (breedOption == correctBreed) {
        Color(0xFF479c35) // Answered correctly
    } else {
        Color.Red.copy(alpha = 0.8f)  // Answered wrongly
    }
}

/**
 * Get the result string to display
 */
@VisibleForTesting
fun getResultText(answerState: AnswerState, correctBreed: String): String {
    return when (answerState) {
        AnswerState.ANSWERING -> ""
        AnswerState.ANSWERED_CORRECTLY -> "Great job, you're correct! \uD83D\uDCAF"
        AnswerState.ANSWERED_WRONGLY -> "Whoops! The breed is $correctBreed."
    }
}

@Preview(showBackground = true)
@Composable
fun BreedChallengeScreenPreview() {
    BreedMasterTheme {
        BreedChallengeScreen(BreedChallengeViewModel())
    }
}
