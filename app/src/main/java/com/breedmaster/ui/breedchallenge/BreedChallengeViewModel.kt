package com.breedmaster.ui.breedchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breedmaster.data.BreedsRepository
import com.breedmaster.data.impl.RemoteBreedsRepository
import com.breedmaster.domain.GetThreeBreedsUseCase
import com.breedmaster.model.Breed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * [ViewModel] for [BreedChallengeScreen]
 */
class BreedChallengeViewModel(
    private val breedsRepo: BreedsRepository = RemoteBreedsRepository(),
    private val getThreeBreedsUseCase: GetThreeBreedsUseCase = GetThreeBreedsUseCase()
) :
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
            val breeds = breedsRepo.getAllBreeds().getOrNull() ?: run {
                Log.e(TAG, "failed to get all breeds")
                // TODO UI error state
                return@launch
            }

            val (breed, imageUrl) = getRandomBreedWithImage(breeds) ?: run {
                Log.e(TAG, "failed to get a breed with image")
                // TODO UI error state
                return@launch
            }

            // Validate the imageUrl
            if (imageUrl.isEmpty()) {
                Log.e(TAG, "invalid image URL")
                // TODO UI error state
                return@launch
            }

            // Populate the new question
            _uiState.value = BreedChallengeUiState(
                question = BreedChallengeQuestion(
                    breed = breed.getDisplayName(),
                    imageUrl = imageUrl,
                    breedOptions = getThreeBreedsUseCase(breeds, breed)
                ),
                answer = BreedChallengeAnswer(
                    state = AnswerState.ANSWERING,
                    breed = "",
                )
            )
        }
    }

    /**
     * User action to answer the question
     */
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