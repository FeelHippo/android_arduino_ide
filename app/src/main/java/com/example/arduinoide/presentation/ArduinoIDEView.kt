package com.example.arduinoide.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arduinoide.ui.state.ArduinoIDEStateData
import com.example.arduinoide.ui.theme.ArduinoIDETheme

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

@Composable
fun ArduinoIDEView(
    state: ArduinoIDEStateData,
    verifySketch: (content: List<String>?) -> Unit,
    uploadSketch: (content: List<String>?) -> Unit,
    writeSketch: (fileName: String?, content: List<String>?) -> Unit,
    deleteSketch: () -> Unit,
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
                        onClick = {
                            when (destination.action) {
                                Action.VERIFY -> verifySketch(state.sketchContent)
                                Action.UPLOAD -> uploadSketch(state.sketchContent)
                                Action.SAVE -> writeSketch(state.sketchFileName, state.sketchContent)
                                Action.CLEAR -> deleteSketch()
                            }
                        },
                        icon = {
                            OutlinedButton(
                                onClick = {},
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
        if (state.sketchContent == null) {
            FloatingActionButton(
                onClick = { // TODO: pick .ino file here
                    },
                modifier = modifier.padding(contentPadding)
            ) { }
        }
    }
}