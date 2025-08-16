package com.example.arduinoide.presentation.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.arduinoide.ui.state.ArduinoIDEStateData

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArduinoDefaultScreen(
    updateState: (data: ArduinoIDEStateData) -> Unit,
    writeSketch: (content: List<String>?, uri: Uri?) -> ArduinoIDEStateData,
    modifier: Modifier = Modifier,
) {
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