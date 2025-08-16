package com.example.arduinoide.presentation.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


fun formatArduinoCommandLine(rawCommandLine: String): AnnotatedString {
    return buildAnnotatedString {
        val lineSeparatedBySpace = rawCommandLine.split(" ")
        // each space-separated block of the command line
        // e.g. "int varName = analogRead(A0);" => ["int", "varname", "=", "analogRead(A0);"]
        lineSeparatedBySpace.forEachIndexed { bySpaceIndex, bySpace ->
            // e.g. "Serial.begin(9600);" => ["Serial", "begin(9600);"]
            bySpace.split(".").forEachIndexed { byDotIndex, byDot ->
                // each dot-separated block of the space-separated block
                var added = false
                // words containing Arduino commands
                ArduinoLanguageStyle.entries.forEach { languageStyle ->
                    val arduinoCommand = languageStyle.commands.find { command ->
                        // word contains arduino command
                        byDot.contains(command) && !added
                    }
                    if (arduinoCommand != null) {
                        val start = byDot.indexOf(arduinoCommand)
                        val end = start + arduinoCommand.length

                        // add dot when necessary
                        if (byDotIndex > 0) {
                            append(".")
                        }

                        // prefix to command
                        if (start != 0) {
                            append(byDot.substring(0, start))
                        }

                        // command
                        withStyle(
                            style = SpanStyle(
                                color = languageStyle.color
                            )
                        ) {
                            append(byDot.substring(start, end))
                        }

                        // suffix to command
                        append(byDot.substring(end, byDot.length))

                        added = true
                    }
                }
                // a word that does not contain an Arduino command
                if (!added) {
                    append(byDot)
                }
            }
            // do not add trailing space to last element of command line
            // this allows TransformedText() to compile
            if (bySpaceIndex < lineSeparatedBySpace.size - 1) {
                // separate words
                append(" ")
            }
        }
    }
}