package com.arthurabreu.allthingsandroid.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object CpfUtils {
    private val CPF_REGEX = """\d{3}\.\d{3}\.\d{3}-\d{2}""".toRegex()

    fun formatPartialCpf(digits: String): String {
        return buildString {
            digits.forEachIndexed { index, char ->
                when (index) {
                    3, 6 -> append(".$char")
                    9 -> append("-$char")
                    else -> append(char)
                }
            }
        }
    }

    fun isCpfValid(cpf: String): Boolean {
        return cpf.matches(CPF_REGEX)
    }
}

class CpfVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text
        val formatted = CpfUtils.formatPartialCpf(digits)

        val offsetMapping = object : OffsetMapping {
            // Maps original digit index to formatted position
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 6 -> offset + 1  // Account for first dot
                    offset <= 9 -> offset + 2  // Account for first and second dot
                    else -> offset + 3         // Account for both dots and hyphen
                }
            }

            // Maps formatted position back to original digit index
            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 3 -> offset
                    offset <= 7 -> offset - 1
                    offset <= 11 -> offset - 2
                    else -> offset - 3
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = offsetMapping
        )
    }
}