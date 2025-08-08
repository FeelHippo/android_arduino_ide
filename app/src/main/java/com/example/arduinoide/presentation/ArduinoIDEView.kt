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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
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

val VARIABLES = arrayOf(
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
    ARDUINO_VARIABLES(Color(0xffe8bba8), VARIABLES),
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
                        Text(buildAnnotatedString {
                            string.split(" ").forEach {
                                var added = false
                                ArduinoLanguageStyle.entries.forEach { languageStyle ->
                                    if (
                                        languageStyle.commands.any { command ->
                                            it.contains(command)
                                        }
                                    ) {
                                        withStyle(
                                            style = SpanStyle(
                                                color = languageStyle.color
                                            )
                                        ) {
                                            append(it.substringBefore("("))
                                        }
                                        val openParentheses = it.indexOf("(")
                                        if (openParentheses != -1) {
                                            append("(")
                                            append(it.substringAfter("("))
                                        }
                                        added = true
                                    }
                                }
                                if (!added) {
                                    append(it)
                                }
                                append(" ")
                            }
                        })
                    }
                }
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