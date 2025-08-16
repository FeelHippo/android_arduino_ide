package com.example.arduinoide.presentation

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.arduinoide.presentation.navigator.ArduinoNavigationBar
import com.example.arduinoide.presentation.screens.ArduinoDefaultScreen
import com.example.arduinoide.presentation.screens.ArduinoSketchScreen
import com.example.arduinoide.ui.state.ArduinoIDEStateData

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
            ArduinoNavigationBar(
                state,
                updateState,
                verifySketch,
                uploadSketch,
                writeSketch,
                deleteSketch,
                modifier,
            )
        },
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
                ArduinoDefaultScreen(updateState, writeSketch, modifier)
            } else {
                ArduinoSketchScreen(state, modifier)
            }
        }
    }
}
