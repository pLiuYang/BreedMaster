package com.breedmaster.data.impl

import android.util.Log
import com.breedmaster.data.BreedsRepository
import com.breedmaster.data.remote.BreedsApiClient
import com.breedmaster.data.remote.BreedsApiService
import com.breedmaster.data.remote.getPathForApi
import com.breedmaster.data.remote.isSuccess
import com.breedmaster.data.remote.toBreedList
import com.breedmaster.model.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteBreedsRepository(private val apiService: BreedsApiService = BreedsApiClient.breedsApiService) :
    BreedsRepository {

    override suspend fun getAllBreeds(): Result<List<Breed>> {
        Log.i(TAG, "getAllBreeds")
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getAllBreeds()
                if (result.isSuccess() && result.message != null) {
                    Result.success(result.message.toBreedList())
                } else {
                    Result.failure<List<Breed>>(IllegalArgumentException("something wrong with the response"))
                }
            } catch (e: Exception) {
                Result.failure<List<Breed>>(e)
            }
        }
    }

    override suspend fun getRandomImageForBreed(breed: Breed): Result<String> {
        Log.i(TAG, "getRandomImageForBreed for ${breed.getPathForApi()}")
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getBreedImages(breed.getPathForApi())
                if (result.isSuccess() && result.message != null) {
                    Result.success(result.message)
                } else {
                    Result.failure<String>(IllegalArgumentException("something wrong with the response"))
                }
            } catch (e: Exception) {
                Result.failure<String>(e)
            }
        }
    }
}

private const val TAG = "RemoteBreedsRepository"