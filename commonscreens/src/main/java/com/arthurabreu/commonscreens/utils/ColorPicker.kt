package com.arthurabreu.commonscreens.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPickerDialog(
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedColor by remember { mutableStateOf(
        ColorEnvelope(Color.Blue, "Blue", false)
    ) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Progress Color") },
        text = {
            Column {
                ColorPicker(
                    onColorChange = { colorEnvelope: ColorEnvelope ->
                        selectedColor = selectedColor.copy(color = colorEnvelope.color)
                    }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onColorSelected(selectedColor.color)
                onDismiss()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ColorPicker(
    onColorChange: (ColorEnvelope) -> Unit
) {
    val controller = rememberColorPickerController()

    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.White)
    ) {
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                onColorChange(colorEnvelope)
            }
        )
    }
}