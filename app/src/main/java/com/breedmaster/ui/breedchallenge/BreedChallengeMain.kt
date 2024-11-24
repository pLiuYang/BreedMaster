package com.breedmaster.ui.breedchallenge

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.breedmaster.ui.theme.BreedMasterTheme

@Composable
fun BreedChallengeMain(
    uiState: BreedChallengeUiState.BreedChallenge,
    answerAction: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        // Question
        AsyncImage(
            model = uiState.question.imageUrl,
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

        // This is to cover the edge case where less than two options are passed in
        val numberOfOptions = 3.coerceAtMost(uiState.question.breedOptions.size)
        // Options
        for (i in 0 until numberOfOptions) {
            BreedOption(
                uiState.question.breedOptions[i],
                uiState.question.breed,
                uiState.answer
            ) { answerAction(uiState.question.breedOptions[i]) }
        }

        // Result
        Text(
            text = getResultText(uiState.answer.state, uiState.question.breed),
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge
        )
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
fun BreedChallengeMainPreview() {
    BreedMasterTheme {
        BreedChallengeMain(
            BreedChallengeUiState.BreedChallenge(
                question = BreedChallengeQuestion(
                    breed = "affenpinscher",
                    imageUrl = "https://images.dog.ceo/breeds/hound-english/n02089973_2300.jpg",
                    breedOptions = listOf(
                        "affenpinscher",
                        "borzoi",
                        "buhund", "norwegian"
                    )
                ),
                answer = BreedChallengeAnswer(
                    state = AnswerState.ANSWERING,
                    breed = "",
                ),
            ), {}
        )
    }
}