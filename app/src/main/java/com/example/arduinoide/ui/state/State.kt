package com.example.arduinoide.ui.state

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import com.example.arduinoide.presentation.ArduinoIDEView
import com.example.arduinoide.service_locator.ServiceLocator

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArduinoIDEState(
    context: Context,
) {
    val storage = ServiceLocator(context).getStorage()
    val service = ServiceLocator(context).getService()
    var state by rememberSaveable(stateSaver = ArduinoIDEStateDataSaver) {
        mutableStateOf(
            ArduinoIDEStateData(
                storage.sketchFileUri,
                storage.sketchContent,
            )
        )
    }
    ArduinoIDEView(
        state,
        verifySketch = { content -> service.verifySketch(content) },
        uploadSketch = { content -> service.uploadSketch(content) },
        writeSketch = { content, uri -> storage.writeSketch(content, uri) },
        deleteSketch = { storage.deleteSketch() }
    )
}

data class ArduinoIDEStateData(
    var sketchFileUri: Uri?,
    var sketchContent: List<String>?,
)

val ArduinoIDEStateDataSaver = run {
    val sketchFileUriKey = "sketch_file_uri"
    val sketchContentKey = "sketch_content_key"
    mapSaver(
        save = {
            mapOf(
                sketchFileUriKey to it.sketchFileUri,
                sketchContentKey to it.sketchContent,
            )
        },
        restore = {
            ArduinoIDEStateData(
                it[sketchFileUriKey] as Uri,
                it[sketchContentKey] as List<String>,
            )
        }
    )
}