package com.breedmaster

import com.breedmaster.data.remote.BreedsResponse
import com.breedmaster.data.remote.isSuccess
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class BreedsResponseTest {

    @Test
    fun isSuccess_ShouldReturnTrue_ForSuccessfulStatus() {
        // Arrange
        val response = BreedsResponse(
            message = mapOf("affenpinscher" to emptyList<String>()),
            status = "success"
        )

        // Act
        val result = response.isSuccess()

        // Assert
        assertTrue(result)
    }

    @Test
    fun isSuccess_ShouldReturnFalse_ForFailureStatus() {
        // Arrange
        val response = BreedsResponse(message = null, status = "error")

        // Act
        val result = response.isSuccess()

        // Assert
        assertFalse(result)
    }

    @Test
    fun isSuccess_ShouldReturnFalse_ForEmptyStatus() {
        // Arrange
        val response = BreedsResponse(message = listOf<String>(), status = "")

        // Act
        val result = response.isSuccess()

        // Assert
        assertFalse(result)
    }

    @Test
    fun isSuccess_ShouldReturnFalse_ForNullStatus() {
        // Arrange
        val response = BreedsResponse(message = null, status = null)

        // Act
        val result = response.isSuccess()

        // Assert
        assertFalse(result)
    }
}