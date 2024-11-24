package com.breedmaster.data.remote

import com.breedmaster.model.Breed

/**
 * Map API response to models
 */
fun Map<String, List<String>>.toBreedList(): List<Breed> {
    return buildList {
        entries.forEach {
            if (it.value.isEmpty()) {
                add(Breed(name = it.key))
            } else {
                it.value.forEach { subBreed ->
                    add(Breed(name = it.key, subBreed = subBreed))
                }
            }
        }
    }
}

/**
 * Map a [Breed] to path in API request
 */
fun Breed.getPathForApi(): String {
    return if (subBreed.isEmpty()) {
        name
    } else {
        "$name/$subBreed"
    }
}