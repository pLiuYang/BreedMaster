package com.breedmaster.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Breeds API service
 */
interface BreedsApiService {

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedsResponse<Map<String, List<String>>>

    @GET("breed/{breed}/images/random")
    suspend fun getBreedImages(@Path("breed", encoded = true) breed: String): BreedsResponse<String>

}