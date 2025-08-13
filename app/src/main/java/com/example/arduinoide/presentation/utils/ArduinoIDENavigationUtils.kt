package com.example.arduinoide.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Save
import androidx.compose.ui.graphics.vector.ImageVector


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