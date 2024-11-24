package com.breedmaster.ui.breedchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breedmaster.data.BreedsRepository
import com.breedmaster.data.impl.FakeBreedsRepository
import com.breedmaster.model.Breed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * [ViewModel] for [BreedChallengeScreen]
 */
class BreedChallengeViewModel(private val breedsRepo: BreedsRepository = FakeBreedsRepository()) :
    ViewModel() {

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

    init {
        refreshQuestion()
    }

    fun refreshQuestion() {
        // TODO: Set UI to loading state

        viewModelScope.launch {
            breedsRepo.getAllBreeds().getOrNull()?.let { breeds ->
                getRandomBreedWithImage(breeds)?.let { (breed, imageUrl) ->
                    _uiState.update {
                        BreedChallengeUiState(
                            question = BreedChallengeQuestion(
                                breed = breed.getDisplayName(),
                                imageUrl = imageUrl,
                                breedOptions = getThreeRandomly(breeds)
                            ),
                            answer = BreedChallengeAnswer(
                                state = AnswerState.ANSWERING,
                                breed = "",
                            )
                        )
                    }
                } ?: run {
                    // TODO UI error state
                }

            } ?: run {
                // TODO UI error state
            }
        }
    }

    fun answer(answer: String) {
        if (uiState.value.answer.state != AnswerState.ANSWERING) {
            Log.i(TAG, "Only allowing answering once")
            return
        }

        _uiState.update {
            it.copy(
                answer = BreedChallengeAnswer(
                    state = if (answer == it.question.breed) AnswerState.ANSWERED_CORRECTLY else AnswerState.ANSWERED_WRONGLY,
                    breed = answer,
                )
            )
        }
    }

    private suspend fun getRandomBreedWithImage(breeds: List<Breed>): Pair<Breed, String>? {
        if (breeds.isEmpty()) return null

        val randomBreed = breeds[Random.nextInt(breeds.size)]
        return breedsRepo.getRandomImageForBreed(randomBreed).getOrNull()?.let { imageUrl ->
            Pair(randomBreed, imageUrl)
        }
    }

    private fun getThreeRandomly(breeds: List<Breed>): List<String> {
        return breeds.shuffled().take(3).map { it.getDisplayName() }
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