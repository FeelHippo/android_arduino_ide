package com.example.arduinoide.presentation

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.arduinoide.ui.state.ArduinoIDEStateData

enum class Action { VERIFY, UPLOAD, SAVE, CLEAR }

enum class Destination(
    val action: Action,
    val icon: ImageVector,
    val contentDescription: String,
) {
    VERIFY(Action.VERIFY, Icons.Outlined.Check, "Verify"),
    UPLOAD(Action.UPLOAD, Icons.Outlined.ArrowForward, "Upload"),
    SAVE(Action.SAVE, Icons.Outlined.Save, "Save"),
    CLEAR(Action.CLEAR, Icons.Outlined.Delete, "Clear"),
}

// TODO(Filippo): eventually add all items from  functions, values (variables and constants), and structure
val DIGITAL_ANALOG_IO = arrayOf(
    "digitalRead",
    "digitalWrite",
    "pinMode",
    "analogRead",
    "analogReadResolution",
    "analogReference",
    "analogWrite",
    "analogWriteResolution",
)

val MATH = arrayOf(
    "abs",
    "constrain",
    "map",
    "max",
    "min",
    "pow",
    "sq",
    "sqrt",
)

val BITS_AND_BYTES = arrayOf(
    "bit",
    "bitClear",
    "bitRead",
    "bitSet",
    "bitWrite",
    "highByte",
    "lowByte",
)

val TIME = arrayOf(
    "delay",
    "delayMicroseconds",
    "micros",
    "millis",
)

val RANDOM = arrayOf(
    "random",
    "randomSeed",
)

val COMMUNICATION = arrayOf(
    "SPI",
    "Print",
    "Serial",
    "Stream",
    "Wire",
    "begin",
    "println",
    "print",
)

val VARIABLES_DATA_TYPES = arrayOf(
    "array",
    "bool",
    "boolean",
    "byte",
    "char",
    "double",
    "float",
    "int",
    "long",
    "short",
    "size_t",
    "string",
    "void",
    "word",
)

val VARIABLES_SCOPE_AND_QUALIFIERS = arrayOf(
    "const",
    "scope",
    "static",
    "volatile",
)

val SKETCH = arrayOf(
    "loop",
    "setup",
)

val CONTROL_STRUCTURE = arrayOf(
    "break",
    "continue",
    "do",
    "while",
    "else",
    "for",
    "goto",
    "if",
    "return",
    "switch",
    "case",
    "while",
)

enum class ArduinoLanguageStyle(val color: Color, val commands: Array<String>) {
    ARDUINO_DIGITAL_ANALOG_IO(Color(0xff81d5c1), DIGITAL_ANALOG_IO),
    ARDUINO_MATH(Color(0xffdddccd), MATH),
    ARDUINO_BITS_AND_BYTES(Color(0xfff9f6eb), BITS_AND_BYTES),
    ARDUINO_TIME(Color(0xfff3d88e), TIME),
    ARDUINO_RANDOM(Color(0xfff3d88e), RANDOM),
    ARDUINO_COMMUNICATION(Color(0xfff3d88e), COMMUNICATION),
    ARDUINO_DATA_TYPES(Color(0xffe8bba8), VARIABLES_DATA_TYPES),
    ARDUINO_SCOPE_AND_QUALIFIERS(Color(0xffe8bba8), VARIABLES_SCOPE_AND_QUALIFIERS),
    ARDUINO_SKETCH(Color(0xfff3d88e), SKETCH),
    ARDUINO_CONTROL_STRUCTURE(Color(0xFFA5CA9C), CONTROL_STRUCTURE),
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArduinoIDEView(
    state: ArduinoIDEStateData,
    updateState: (data: ArduinoIDEStateData) -> Unit,
    verifySketch: (content: List<String>?) -> Unit,
    uploadSketch: (content: List<String>?) -> Unit,
    writeSketch: (content: List<String>?, uri: Uri?) -> ArduinoIDEStateData,
    deleteSketch: () -> ArduinoIDEStateData,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                Destination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            OutlinedButton(
                                onClick = {
                                    when (destination.action) {
                                        Action.VERIFY -> verifySketch(state.sketchContent)
                                        Action.UPLOAD -> uploadSketch(state.sketchContent)
                                        Action.SAVE -> writeSketch(state.sketchContent, state.sketchFileUri)
                                        Action.CLEAR -> {
                                            val newState = deleteSketch()
                                            updateState(newState)
                                        }
                                    }
                                },
                                shape = CircleShape,
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                                    disabledContentColor = MaterialTheme.colorScheme.tertiary,
                                ),
                                modifier = modifier.size(48.dp),
                            ) {
                                Icon(
                                    destination.icon,
                                    contentDescription = destination.contentDescription,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = modifier,
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = if (state.sketchContent == null) Arrangement.Bottom else Arrangement.Top,
            horizontalAlignment = if (state.sketchContent == null) Alignment.End else Alignment.Start,
        ) {
            if (state.sketchContent == null) {
                var isClicked by remember { mutableStateOf(false) }
                OutlinedButton(
                    onClick = { isClicked = true },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                        disabledContentColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    modifier = modifier.size(64.dp).absoluteOffset((-32).dp, y = (-32).dp),
                ) {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = "Load a sketch",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = modifier,
                    )
                }
                if (isClicked) {
                    OpenSketchFile(writeSketch, updateState)
                }
            } else {
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
        }
    }
}

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

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun OpenSketchFile(
    writeSketch: (content: List<String>?, uri: Uri?) -> ArduinoIDEStateData,
    updateState: (data: ArduinoIDEStateData) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { sketchUri ->
        if (sketchUri != null) {
            intArrayOf(
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            ).forEach {
                context.contentResolver.takePersistableUriPermission(
                    sketchUri,
                    it,
                )
            }
            val inputStream = context.contentResolver.openInputStream(sketchUri)
            val lines = inputStream?.bufferedReader().use { it?.readLines() }
            val newState = writeSketch(lines, sketchUri)
            updateState(newState)
            inputStream?.close()
        }
    }

    SideEffect { launcher.launch(arrayOf("*/*")) }
}