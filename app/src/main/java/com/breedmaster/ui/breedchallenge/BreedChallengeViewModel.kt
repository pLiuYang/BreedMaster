package com.breedmaster.ui.breedchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * [ViewModel] for [BreedChallengeScreen]
 */
class BreedChallengeViewModel : ViewModel() {

    private val initialState = BreedChallengeUiState(
        BreedChallengeQuestion(
            "affenpinscher",
            "https://images.dog.ceo/breeds/hound-english/n02089973_2300.jpg",
            listOf("affenpinscher", "hound", "cavapoo"),
        ),
        BreedChallengeAnswer(
            state = AnswerState.ANSWERING,
            breed = "",
        ),
    )
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<BreedChallengeUiState> = _uiState

    fun answer(answer: String) {
        if (uiState.value.answer.state != AnswerState.ANSWERING) {
            Log.i(TAG, "Only allowing answering once")
            return
        }

        _uiState.value = _uiState.value.let {
            it.copy(
                answer = BreedChallengeAnswer(
                    state = if (answer == it.question.breed) AnswerState.ANSWERED_CORRECTLY else AnswerState.ANSWERED_WRONGLY,
                    breed = answer,
                )
            )
        }
    }
}

/**
 * UI state for [BreedChallengeScreen]
 */
data class BreedChallengeUiState(
    val question: BreedChallengeQuestion,
    val answer: BreedChallengeAnswer,
)

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

private const val TAG = "BreedChallengeViewModel"