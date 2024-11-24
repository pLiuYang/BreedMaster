package com.breedmaster

import com.breedmaster.domain.GetThreeBreedsUseCase
import com.breedmaster.model.Breed
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetThreeBreedsUseCaseTest {

    private val getThreeBreedsUseCase = GetThreeBreedsUseCase()

    @Test
    fun invoke_ShouldReturnListWithThreeBreedsIncludingPivot() {
        // Arrange
        val breed1 = Breed("affenpinscher")
        val breed2 = Breed("hound")
        val breed3 = Breed("cavapoo")
        val breed4 = Breed("poodle")
        val breedList = listOf(breed1, breed2, breed3, breed4)
        val pivotBreed = breed1

        // Act
        val result = getThreeBreedsUseCase(breedList, pivotBreed)

        // Assert
        assertEquals(3, result.size) // The result should have exactly 3 items
        assertTrue(result.contains(pivotBreed.getDisplayName())) // Pivot breed should be in the list
    }

    @Test
    fun invoke_ShouldShuffleResultBreeds() {
        // Arrange
        val breed1 = Breed("affenpinscher")
        val breed2 = Breed("hound")
        val breed3 = Breed("cavapoo")
        val breed4 = Breed("poodle")
        val breedList = listOf(breed1, breed2, breed3, breed4)
        val pivotBreed = breed1

        // Act
        val result1 = getThreeBreedsUseCase(breedList, pivotBreed)
        val result2 = getThreeBreedsUseCase(breedList, pivotBreed)

        // Assert
        // Ensure that the result is shuffled, so it should not be the same as the previous one
        assertTrue(result1 != result2)
    }

    @Test
    fun invoke_ShouldNotReturnPivotBreedMoreThanOnce() {
        // Arrange
        val breed1 = Breed("affenpinscher")
        val breed2 = Breed("hound")
        val breed3 = Breed("cavapoo")
        val breed4 = Breed("poodle")
        val breedList = listOf(breed1, breed2, breed3, breed4)
        val pivotBreed = breed1

        // Act
        val result = getThreeBreedsUseCase(breedList, pivotBreed)

        // Assert
        assertEquals(3, result.size)
        assertTrue(result.contains(pivotBreed.getDisplayName())) // Pivot breed should be in the list
        assertTrue(result.filter { it == pivotBreed.getDisplayName() }.size == 1) // Pivot breed should appear only once
    }

    @Test
    fun invoke_ShouldHandleListWithLessThanThreeBreeds() {
        // Arrange
        val breed1 = Breed("affenpinscher")
        val breed2 = Breed("hound")
        val breedList = listOf(breed1, breed2)
        val pivotBreed = breed1

        // Act
        val result = getThreeBreedsUseCase(breedList, pivotBreed)

        // Assert
        assertEquals(2, result.size) // Should return only 2 breeds if there are less than 3 options
        assertTrue(result.contains(pivotBreed.getDisplayName())) // Pivot breed should be in the list
    }
}