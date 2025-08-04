package com.example.arduinoide

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.arduinoide.ui.state.ArduinoIDEState
import com.example.arduinoide.ui.theme.ArduinoIDETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val context: Context = this@MainActivity
        setContent {
            ArduinoIDETheme(darkTheme = true) {
                ArduinoIDEState(context)
            }
        }
    }
}