package com.breedmaster.ui.breedchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breedmaster.data.BreedsRepository
import com.breedmaster.data.impl.RemoteBreedsRepository
import com.breedmaster.domain.GetThreeBreedsUseCase
import com.breedmaster.model.Breed
import com.breedmaster.ui.breedchallenge.BreedChallengeUiState.BreedChallenge
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
) : ViewModel() {

    private val _uiState = MutableStateFlow<BreedChallengeUiState>(BreedChallengeUiState.Loading)
    val uiState: StateFlow<BreedChallengeUiState> = _uiState

    init {
        refreshQuestion()
    }

    fun refreshQuestion() {
        Log.i(TAG, "refreshQuestion")
        _uiState.value = BreedChallengeUiState.Loading

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
            _uiState.value = BreedChallenge(
                question = BreedChallengeQuestion(
                    breed = breed.getDisplayName(),
                    imageUrl = imageUrl,
                    breedOptions = getThreeBreedsUseCase(breeds, breed)
                ),
                answer = BreedChallengeAnswer(
                    state = AnswerState.ANSWERING,
                    breed = "",
                ),
            )
        }
    }

    /**
     * User action to answer the question
     */
    fun answer(answer: String) {
        val state = _uiState.value
        if (state !is BreedChallenge) {
            Log.w(TAG, "Unsupported action in this state")
            return
        }
        if (state.answer.state != AnswerState.ANSWERING) {
            Log.w(TAG, "Only allowing answering once")
            return
        }

        _uiState.update {
            state.copy(
                answer = BreedChallengeAnswer(
                    state = if (answer == state.question.breed) AnswerState.ANSWERED_CORRECTLY else AnswerState.ANSWERED_WRONGLY,
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

private const val TAG = "BreedChallengeViewModel"