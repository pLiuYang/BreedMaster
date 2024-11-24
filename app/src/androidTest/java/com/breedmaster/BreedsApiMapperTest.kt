package com.breedmaster

import com.breedmaster.data.remote.getPathForApi
import com.breedmaster.data.remote.toBreedList
import com.breedmaster.model.Breed
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BreedsApiMapperTest {

    @Test
    fun toBreedList_shouldMapApiResponseToCorrectBreedListWithoutSubBreed() {
        // Arrange
        val apiResponse = mapOf(
            "affenpinscher" to emptyList(),
            "hound" to listOf("irish")
        )

        // Act
        val result = apiResponse.toBreedList()

        // Assert
        assertEquals(2, result.size)
        assertTrue(result.contains(Breed(name = "affenpinscher")))
        assertTrue(result.contains(Breed(name = "hound", subBreed = "irish")))
    }

    @Test
    fun toBreedList_shouldHandleEmptyMapCorrectly() {
        // Arrange
        val apiResponse = emptyMap<String, List<String>>()

        // Act
        val result = apiResponse.toBreedList()

        // Assert
        assertTrue(result.isEmpty())  // The result should be an empty list
    }

    @Test
    fun getPathForApi_shouldReturnCorrectPathForBreedWithoutSubBreed() {
        // Arrange
        val breed = Breed(name = "affenpinscher")

        // Act
        val result = breed.getPathForApi()

        // Assert
        assertEquals("affenpinscher", result)
    }

    @Test
    fun getPathForApi_shouldReturnCorrectPathForBreedWithSubBreed() {
        // Arrange
        val breed = Breed(name = "hound", subBreed = "irish")

        // Act
        val result = breed.getPathForApi()

        // Assert
        assertEquals("hound/irish", result)
    }
}