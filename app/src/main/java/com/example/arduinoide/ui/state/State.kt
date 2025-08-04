package com.example.arduinoide.ui.state

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.arduinoide.presentation.ArduinoIDEView
import com.example.arduinoide.service_locator.ServiceLocator

@Composable
fun ArduinoIDEState(
    context: Context,
) {
    val storage = ServiceLocator(context).getStorage()
    val service = ServiceLocator(context).getService()
    var state by rememberSaveable(stateSaver = ArduinoIDEStateDataSaver) {
        mutableStateOf(
            ArduinoIDEStateData(
                storage.sketchFileName,
                storage.sketchContent,
            )
        )
    }
    ArduinoIDEView(
        state,
        verifySketch = { content -> service.verifySketch(content) },
        uploadSketch = { content -> service.uploadSketch(content) },
        writeSketch = { fileName, content -> storage.writeSketch(fileName, content) },
        deleteSketch = { storage.deleteSketch() }
    )
}

data class ArduinoIDEStateData(
    var sketchFileName: String?,
    var sketchContent: List<String>?,
)

val ArduinoIDEStateDataSaver = run {
    val sketchFileNameKey = "sketch_file_name"
    val sketchContentKey = "sketch_content_key"
    mapSaver(
        save = {
            mapOf(
                sketchFileNameKey to it.sketchFileName,
                sketchContentKey to it.sketchContent,
            )
        },
        restore = {
            ArduinoIDEStateData(
                it[sketchFileNameKey] as String,
                it[sketchContentKey] as List<String>,
            )
        }
    )
}