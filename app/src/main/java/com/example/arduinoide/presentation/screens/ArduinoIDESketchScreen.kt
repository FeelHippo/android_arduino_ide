package com.example.arduinoide.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import com.example.arduinoide.presentation.utils.formatArduinoCommandLine
import com.example.arduinoide.ui.state.ArduinoIDEStateData

@Composable
fun ArduinoSketchScreen(
    state: ArduinoIDEStateData,
    modifier: Modifier = Modifier,
) {
    state.sketchContent!!.forEachIndexed { index, string ->
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.size(32.dp),
            ) {
                Text(index.toString(), color = MaterialTheme.colorScheme.tertiary)
            }
            Box(modifier = modifier.size(12.dp))

            // https://stackoverflow.com/a/76897669/10708345
            val formattedArduinoCommandLine = formatArduinoCommandLine(string)
            val commandLine = remember {
                mutableStateOf(
                    TextFieldValue(
                        formattedArduinoCommandLine,
                        selection = TextRange(
                            index = formattedArduinoCommandLine.length,
                        )
                    )
                )
            }
            BasicTextField(
                commandLine.value,
                onValueChange = {
                    commandLine.value = it
                },
                visualTransformation = {
                    TransformedText(
                        text = formatArduinoCommandLine(commandLine.value.text),
                        offsetMapping = OffsetMapping.Identity,
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = Color.White),
            )
        }
    }
}