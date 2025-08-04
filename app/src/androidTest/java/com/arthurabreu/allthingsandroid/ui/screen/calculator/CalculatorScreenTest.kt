package com.arthurabreu.allthingsandroid.ui.screen.calculator

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.arthurabreu.allthingsandroid.ui.screen.BaseComposeKoinTest
import com.arthurabreu.allthingsandroid.ui.theme.AllThingsAndroidTheme
import com.arthurabreu.allthingsandroid.ui.viewmodel.calculator.CalculatorViewModel
import org.junit.Rule
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

class CalculatorScreenTest : BaseComposeKoinTest() {

    private val displayTag = "CalculatorDisplay"

    override fun provideTestModules(): List<Module> {
        return listOf(
            module { factory { CalculatorViewModel() } }
        )
    }

    override fun setComposeContent() {
        val viewModel: CalculatorViewModel = getKoin().get()
        composeTestRule.setContent {
            AllThingsAndroidTheme {
                CalculatorScreen(viewModel = viewModel)
            }
        }
        composeTestRule.waitForIdle()
    }

    private fun clickButton(symbol: String) {
        composeTestRule.onNodeWithTag("button_$symbol").performClick()
        composeTestRule.waitForIdle()
    }

    private fun assertDisplay(expectedText: String) {
        composeTestRule.onNodeWithTag(displayTag).assertTextEquals(expectedText)
    }

    @Test
    fun testInitialDisplayIsEmpty() {
        assertDisplay("")
    }

    @Test
    fun testNumberInputAndAddition() {
        clickButton("1")
        assertDisplay("1")
        clickButton("+")
        assertDisplay("1+")
        clickButton("2")
        assertDisplay("1+2")
        clickButton("=")
        assertDisplay("3")
    }

    @Test
    fun testMaxDigitsLimit() {
        val nineButtonTag = "button_9"
        repeat(8) {
            composeTestRule.onNodeWithTag(nineButtonTag).performClick()
        }
        composeTestRule.waitForIdle()
        assertDisplay("99999999")

        composeTestRule.onNodeWithTag(nineButtonTag).performClick()
        composeTestRule.waitForIdle()
        assertDisplay("99999999")

        composeTestRule.onNodeWithText("999999999").assertDoesNotExist()
    }

    @Test
    fun testSubtraction() {
        clickButton("5")
        assertDisplay("5")
        clickButton("-")
        assertDisplay("5-")
        clickButton("2")
        assertDisplay("5-2")
        clickButton("=")
        assertDisplay("3")
    }

    @Test
    fun testMultiplication() {
        clickButton("3")
        assertDisplay("3")
        clickButton("x")
        assertDisplay("3*")
        clickButton("4")
        assertDisplay("3*4")
        clickButton("=")
        assertDisplay("12")
    }

    @Test
    fun testDivision() {
        clickButton("8")
        assertDisplay("8")
        clickButton("/")
        assertDisplay("8/")
        clickButton("2")
        assertDisplay("8/2")
        clickButton("=")
        assertDisplay("4")
    }

    @Test
    fun testDivisionByZero_DisplaysErrorOrSpecificMessage() {
        clickButton("5")
        clickButton("/")
        assertDisplay("5/")
        clickButton("0")
        assertDisplay("5/0")
        clickButton("=")
        assertDisplay("Infinity")
    }

    @Test
    fun testClearButton_ResetsDisplayToZeroFromNumberInput() {
        clickButton("1")
        assertDisplay("1")
        clickButton("2")
        assertDisplay("12")
        clickButton("3")
        assertDisplay("123")
        clickButton("AC")
        assertDisplay("")
    }

    @Test
    fun testClearButton_ClearsCurrentEntryAfterOperator() {
        clickButton("1")
        assertDisplay("1")
        clickButton("+")
        assertDisplay("1+")
        clickButton("2")
        assertDisplay("1+2")
        clickButton("AC")
        assertDisplay("")
        clickButton("3")
        assertDisplay("3")
        clickButton("=")
        assertDisplay("3")
    }

    @Test
    fun testDecimalInput() {
        clickButton("0")
        assertDisplay("0")
        clickButton(".")
        assertDisplay("0.")
        clickButton("5")
        assertDisplay("0.5")
    }

    @Test
    fun testMultipleDecimalPoints_AreIgnoredInSameNumber() {
        clickButton("1")
        assertDisplay("1")
        clickButton(".")
        assertDisplay("1.")
        clickButton("2")
        assertDisplay("1.2")
        clickButton(".")
        assertDisplay("1.2")
        clickButton("3")
        assertDisplay("1.23")
    }

    @Test
    fun testOperationsWithDecimalNumbers() {
        clickButton("1")
        assertDisplay("1")
        clickButton(".")
        assertDisplay("1.")
        clickButton("5")
        assertDisplay("1.5")
        clickButton("+")
        assertDisplay("1.5+")
        clickButton("2")
        assertDisplay("1.5+2")
        clickButton(".")
        assertDisplay("1.5+2.")
        clickButton("5")
        assertDisplay("1.5+2.5")
        clickButton("=")
        assertDisplay("4")
    }

    @Test
    fun testChainingOperations_ImmediateExecution() {
        clickButton("1")
        assertDisplay("1")
        clickButton("0")
        assertDisplay("10")
        clickButton("+")
        assertDisplay("10+")
        clickButton("5")
        assertDisplay("10+5")
        clickButton("-")
        assertDisplay("10-5")
        clickButton("=")
        assertDisplay("5")
    }

    @Test
    fun testPressingOperatorAfterEquals_StartsNewCalculationWithResult() {
        clickButton("1")
        clickButton("+")
        clickButton("2")
        clickButton("=")
        assertDisplay("3")

        clickButton("+")
        assertDisplay("3+")
        clickButton("5")
        assertDisplay("3+5")
        clickButton("=")
        assertDisplay("8")
    }

    @Test
    fun testNegativeNumbers_ResultOfSubtraction() {
        clickButton("2")
        assertDisplay("2")
        clickButton("-")
        assertDisplay("2-")
        clickButton("5")
        assertDisplay("2-5")
        clickButton("=")
        assertDisplay("-3")
    }

    @Test
    fun testStartingWithOperator_UsesZeroAsFirstOperandOrIgnores() {
        clickButton("+")
        assertDisplay("")
        clickButton("5")
        assertDisplay("5")
        clickButton("=")
        assertDisplay("5")
    }

    @Test
    fun testMultipleOperatorPresses_UsesLastOperator() {
        clickButton("1")
        assertDisplay("1")
        clickButton("0")
        assertDisplay("10")
        clickButton("+")
        assertDisplay("10+")
        clickButton("-")
        assertDisplay("10-")
        clickButton("3")
        assertDisplay("10-3")
        clickButton("=")
        assertDisplay("7")
    }

    @Test
    fun testVeryLargeNumberCalculation_WithoutLimits_Stops_at_eight_character() {
        clickButton("9")
        assertDisplay("9")
        clickButton("0")
        assertDisplay("90")
        clickButton("0")
        assertDisplay("900")
        clickButton("0")
        assertDisplay("9000")
        clickButton("0")
        assertDisplay("90000")
        clickButton("0")
        assertDisplay("900000")
        clickButton("0")
        assertDisplay("9000000")
        clickButton("0")
        assertDisplay("90000000")
        clickButton("x")
        clickButton("5")
        assertDisplay("90000000")
    }
}