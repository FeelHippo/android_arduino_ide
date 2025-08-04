package com.example.arduinoide.data

import android.content.SharedPreferences
import androidx.core.content.edit
import java.io.File

class ArduinoStorage(preferences: SharedPreferences) {
    val sharedPreferences: SharedPreferences = preferences
    val sketchFileMemoryLocation = "arduino_ide_sketch_file_name"
    var sketchFileName: String? = null
    var sketchContent: List<String>? = null

    init { readSketch() }

    fun readSketch() {
        val sketchOnDisplay = sharedPreferences.getString(sketchFileMemoryLocation, null)
        if (sketchOnDisplay != null) {
            sketchFileName = sketchOnDisplay
            sketchContent = File(sketchFileName!!).readLines()
        }
    }

    fun writeSketch(fileName: String?, content: List<String>?) {
        if (fileName != null && content != null) {
            sketchFileName = fileName
            sketchContent = content
            sharedPreferences.edit {
                putString(sketchFileMemoryLocation, sketchFileName)
            }
        }
    }

    fun deleteSketch() {
        sketchFileName = null
        sketchContent = null
        sharedPreferences.edit {
            remove(sketchFileMemoryLocation)
        }
    }
}