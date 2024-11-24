package com.breedmaster.model

/**
 * Model class for dog breeds
 */
data class Breed(val name: String, val subBreed: String = "") {

    fun getDisplayName(): String {
        return if (subBreed.isEmpty()) {
            name.capFirstLetter()
        } else {
            "${subBreed.capFirstLetter()} $name"
        }
    }

    private fun String.capFirstLetter(): String {
        return replaceFirstChar { it.uppercase() }
    }
}
