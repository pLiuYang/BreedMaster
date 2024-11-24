package com.breedmaster.data.impl

import com.breedmaster.data.BreedsRepository
import com.breedmaster.model.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of BreedsRepository that returns a hardcoded list of
 * breeds after some delay in a background thread.
 */
class FakeBreedsRepository : BreedsRepository {
    override suspend fun getAllBreeds(): Result<List<Breed>> {
        return withContext(Dispatchers.IO) {
            Result.success(
                listOf(
                    Breed("affenpinscher"),
                    Breed("borzoi"),
                    Breed("buhund", "norwegian")
                )
            )
        }
    }

    override suspend fun getRandomImageForBreed(breed: Breed): Result<String> {
        return withContext(Dispatchers.IO) {
            Result.success("https://images.dog.ceo/breeds/hound-english/n02089973_2300.jpg")
        }
    }
}