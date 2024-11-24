package com.breedmaster

import androidx.compose.ui.graphics.Color
import com.breedmaster.ui.breedchallenge.AnswerState
import com.breedmaster.ui.breedchallenge.BreedChallengeAnswer
import com.breedmaster.ui.breedchallenge.getOptionBgColor
import com.breedmaster.ui.breedchallenge.getResultText
import org.junit.Assert.assertEquals
import org.junit.Test

class BreedChallengeScreenTest {

    // Test for the getOptionBgColor function
    @Test
    fun testGetOptionBgColor_CorrectAnswer() {
        val correctBreed = "affenpinscher"
        val primaryColor = Color.Blue
        val answer = BreedChallengeAnswer(AnswerState.ANSWERED_CORRECTLY, "affenpinscher")

        // Testing when the breed option matches the correct breed
        val resultColor = getOptionBgColor("affenpinscher", correctBreed, answer, primaryColor)
        assertEquals(Color(0xFF479c35), resultColor)  // Color for correct answer (green)
    }

    @Test
    fun testGetOptionBgColor_WrongAnswer() {
        val correctBreed = "affenpinscher"
        val primaryColor = Color.Blue
        val answer = BreedChallengeAnswer(AnswerState.ANSWERED_WRONGLY, "hound")

        // Testing when the breed option does not match the correct breed
        val resultColor = getOptionBgColor("hound", correctBreed, answer, primaryColor)
        assertEquals(Color.Red.copy(alpha = 0.8f), resultColor)  // Color for wrong answer (red)
    }

    @Test
    fun testGetOptionBgColor_Answering() {
        val correctBreed = "affenpinscher"
        val primaryColor = Color.Blue
        val answer = BreedChallengeAnswer(AnswerState.ANSWERING, "hound")

        // Testing when the answer state is ANSWERING (color should be primary)
        val resultColor = getOptionBgColor("hound", correctBreed, answer, primaryColor)
        assertEquals(primaryColor, resultColor)  // Should return primary color
    }

    // Test for the getResultText function
    @Test
    fun testGetResultText_AnsweredCorrectly() {
        val correctBreed = "affenpinscher"
        val answerState = AnswerState.ANSWERED_CORRECTLY

        val resultText = getResultText(answerState, correctBreed)

        assertEquals("Great job, you're correct! \uD83D\uDCAF", resultText)
    }

    @Test
    fun testGetResultText_AnsweredWrongly() {
        val correctBreed = "affenpinscher"
        val answerState = AnswerState.ANSWERED_WRONGLY

        val resultText = getResultText(answerState, correctBreed)

        assertEquals("Whoops! The breed is affenpinscher.", resultText)
    }

    @Test
    fun testGetResultText_Answering() {
        val correctBreed = "affenpinscher"
        val answerState = AnswerState.ANSWERING

        val resultText = getResultText(answerState, correctBreed)

        assertEquals("", resultText)  // No result text while answering
    }
}