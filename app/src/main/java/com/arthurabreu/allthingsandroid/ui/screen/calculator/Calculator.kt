package com.arthurabreu.allthingsandroid.ui.screen.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arthurabreu.allthingsandroid.data.calculator.CalculatorAction
import com.arthurabreu.allthingsandroid.data.calculator.CalculatorOperation
import com.arthurabreu.allthingsandroid.data.calculator.CalculatorState
import com.arthurabreu.allthingsandroid.ui.theme.OrangeYellow2

private val ButtonSpacing = 8.dp
private val NumButtonColor = Color.DarkGray
private val OpButtonColor = OrangeYellow2
private val FuncButtonColor = Color.LightGray

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = ButtonSpacing,
    onAction: (CalculatorAction) -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            val maxChars = 8
            val displayText = state.number1 + (state.operation?.symbol ?: "") + state.number2
            val dynamicFontSize = if (displayText.length > 8) 40.sp else 60.sp
            val limitedDisplayText = displayText.take(maxChars)

            Text(
                text = limitedDisplayText,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("CalculatorDisplay")
                    .padding(40.dp),
                fontWeight = FontWeight.Light,
                fontSize = dynamicFontSize,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
            CalculatorRow(
                listOf(
                    CalculatorButtonData("AC", FuncButtonColor, 2f, 2f) { onAction(CalculatorAction.Clear) },
                    CalculatorButtonData("Del", FuncButtonColor) { onAction(CalculatorAction.Delete) },
                    CalculatorButtonData("/", OpButtonColor) { onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) }
                ),
                buttonSpacing
            )
            CalculatorRow(
                listOf(
                    numButton(7, onAction),
                    numButton(8, onAction),
                    numButton(9, onAction),
                    CalculatorButtonData("x", OpButtonColor) { onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) }
                ),
                buttonSpacing
            )
            CalculatorRow(
                listOf(
                    numButton(4, onAction),
                    numButton(5, onAction),
                    numButton(6, onAction),
                    CalculatorButtonData("-", OpButtonColor) { onAction(CalculatorAction.Operation(CalculatorOperation.Subtract)) }
                ),
                buttonSpacing
            )
            CalculatorRow(
                listOf(
                    numButton(1, onAction),
                    numButton(2, onAction),
                    numButton(3, onAction),
                    CalculatorButtonData("+", OpButtonColor) { onAction(CalculatorAction.Operation(CalculatorOperation.Add)) }
                ),
                buttonSpacing
            )
            CalculatorRow(
                listOf(
                    CalculatorButtonData("0", NumButtonColor, 2f, 2f) { onAction(CalculatorAction.Number(0)) },
                    CalculatorButtonData(".", NumButtonColor) { onAction(CalculatorAction.Decimal) },
                    CalculatorButtonData("=", OpButtonColor) { onAction(CalculatorAction.Calculate) }
                ),
                buttonSpacing
            )
        }
    }
}

data class CalculatorButtonData(
    val symbol: String,
    val color: Color,
    val aspectRatio: Float = 1f,
    val weight: Float = 1f,
    val onClick: () -> Unit
)

@Composable
fun CalculatorRow(buttons: List<CalculatorButtonData>, buttonSpacing: Dp) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        buttons.forEach { btn ->
            CalculatorButton(
                symbol = btn.symbol,
                modifier = Modifier
                    .background(btn.color)
                    .aspectRatio(btn.aspectRatio)
                    .weight(btn.weight),
                onClick = btn.onClick
            )
        }
    }
}

fun numButton(number: Int, onAction: (CalculatorAction) -> Unit) = CalculatorButtonData(
    symbol = number.toString(),
    color = NumButtonColor,
    onClick = { onAction(CalculatorAction.Number(number)) }
)

@Composable
@Preview(showBackground = true)
fun CalculatorPreview() {
    Calculator(
        state = CalculatorState(),
        onAction = {}
    )
}