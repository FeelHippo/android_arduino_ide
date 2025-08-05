package com.example.arduinoide.presentation

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArduinoIDEView(
    state: ArduinoIDEStateData,
    verifySketch: (content: List<String>?) -> Unit,
    uploadSketch: (content: List<String>?) -> Unit,
    writeSketch: (content: List<String>?, uri: Uri?) -> Unit,
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
                                Action.SAVE -> writeSketch(state.sketchContent, state.sketchFileUri)
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
        Column(
            modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = if (state.sketchContent == null) Arrangement.Bottom else Arrangement.Top,
            horizontalAlignment = if (state.sketchContent == null) Alignment.End else Alignment.Start,
        ) {
            if (state.sketchContent == null) {
                var isClicked by remember { mutableStateOf(false) }
                FloatingActionButton(
                    onClick = { isClicked = true },
                    shape = CircleShape,
                    modifier = modifier.padding(32.dp)
                ) {
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
                        modifier = modifier.size(60.dp),
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = "Load a sketch",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = modifier,
                        )
                    }
                }
                if (isClicked) {
                    openSketchFile(writeSketch)
                }
            }
        }
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun openSketchFile(
    writeSketch: (content: List<String>?, uri: Uri?) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { sketchUri ->
        if (sketchUri != null) {
            val inputStream = context.contentResolver.openInputStream(sketchUri)
            val lines = inputStream?.bufferedReader().use { it?.readLines() }
            writeSketch(lines, sketchUri)
            inputStream?.close()
        }
    }

    SideEffect { launcher.launch(arrayOf("*/*")) }
}