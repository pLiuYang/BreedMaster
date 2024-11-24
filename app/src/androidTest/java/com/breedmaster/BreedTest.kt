package com.breedmaster

import com.breedmaster.model.Breed
import org.junit.Assert.assertEquals
import org.junit.Test

class BreedTest {

    @Test
    fun getDisplayName_ShouldCapitalizeName_WhenNoSubBreedProvided() {
        // Arrange
        val breed = Breed(name = "affenpinscher")

        // Act
        val displayName = breed.getDisplayName()

        // Assert
        assertEquals("Affenpinscher", displayName)
    }

    @Test
    fun getDisplayName_ShouldCapitalizeNameAndSubBreed_WhenSubBreedProvided() {
        // Arrange
        val breed = Breed(name = "hound", subBreed = "irish")

        // Act
        val displayName = breed.getDisplayName()

        // Assert
        assertEquals("Irish hound", displayName)
    }

    @Test
    fun getDisplayName_ShouldHandleEmptyNameAndSubBreed() {
        // Arrange
        val breed = Breed(name = "", subBreed = "")

        // Act
        val displayName = breed.getDisplayName()

        // Assert
        assertEquals("", displayName)
    }

    @Test
    fun getDisplayName_ShouldHandleNameWithSpecialCharacters() {
        // Arrange
        val breed = Breed(name = "bulldog-french")

        // Act
        val displayName = breed.getDisplayName()

        // Assert
        assertEquals("Bulldog-french", displayName)
    }

    @Test
    fun getDisplayName_ShouldHandleNameWithSubBreedContainingSpaces() {
        // Arrange
        val breed = Breed(name = "sheepdog", subBreed = "old english")

        // Act
        val displayName = breed.getDisplayName()

        // Assert
        assertEquals("Old english sheepdog", displayName)
    }
}