package com.breedmaster.data.remote

/**
 * Model for API response
 */
data class BreedsResponse<T>(
    val message: T?,
    val status: String?
)

/**
 * Helper function to decide if the request is successful
 */
fun BreedsResponse<*>.isSuccess(): Boolean = status == "success"
