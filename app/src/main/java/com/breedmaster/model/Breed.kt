package com.breedmaster.model

/**
 * Model class for dog breeds
 */
data class Breed(val name: String, val subBreed: String = "") {

    fun getDisplayName(): String {
        return if (subBreed.isEmpty()) {
            name
        } else {
            "$name $subBreed"
        }
    }
}
