package com.breedmaster.data

import com.breedmaster.model.Breed

/**
 * Interface to the breeds data layer
 */
interface BreedsRepository {

    /**
     * Get all breeds
     */
    suspend fun getAllBreeds(): Result<List<Breed>>

    /**
     * Get a random image for a specific breed
     */
    suspend fun getRandomImageForBreed(breed: Breed): Result<String>
}