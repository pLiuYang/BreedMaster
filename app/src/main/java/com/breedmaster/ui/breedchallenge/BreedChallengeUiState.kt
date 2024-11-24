package com.breedmaster.ui.breedchallenge

/**
 * UI state for [BreedChallengeScreen]
 */
sealed class BreedChallengeUiState {
    object Loading : BreedChallengeUiState()

    data class BreedChallenge(
        val question: BreedChallengeQuestion,
        val answer: BreedChallengeAnswer,
    ) : BreedChallengeUiState()

    data class Error(val message: String) : BreedChallengeUiState()
}

/**
 * UI state for the question in [BreedChallengeScreen]
 */
data class BreedChallengeQuestion(
    val breed: String,
    val imageUrl: String,
    val breedOptions: List<String>,
)

/**
 * UI state for the answer in [BreedChallengeScreen]
 */
data class BreedChallengeAnswer(
    val state: AnswerState,
    val breed: String,
)

/**
 * Answering state of the breed challenge
 */
enum class AnswerState {
    ANSWERING,
    ANSWERED_CORRECTLY,
    ANSWERED_WRONGLY,
}
