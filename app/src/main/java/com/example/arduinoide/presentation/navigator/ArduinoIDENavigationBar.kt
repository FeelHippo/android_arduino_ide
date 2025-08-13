package com.example.arduinoide.presentation.navigator

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.arduinoide.ui.state.ArduinoIDEStateData
import com.example.arduinoide.presentation.utils.Action
import com.example.arduinoide.presentation.utils.Destination

@Composable
fun ArduinoNavigationBar(
    state: ArduinoIDEStateData,
    updateState: (data: ArduinoIDEStateData) -> Unit,
    verifySketch: (content: List<String>?) -> Unit,
    uploadSketch: (content: List<String>?) -> Unit,
    writeSketch: (content: List<String>?, uri: Uri?) -> ArduinoIDEStateData,
    deleteSketch: () -> ArduinoIDEStateData,
    modifier: Modifier = Modifier,
) {
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