package com.example.arduinoide.data

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.content.edit
import androidx.core.net.toUri

class ArduinoStorage(context: Context, preferences: SharedPreferences) {
    val globalContext: Context = context
    val sharedPreferences: SharedPreferences = preferences
    val sketchFileUriKey = "arduino_ide_sketch_uri"
    var sketchFileUri: Uri? = null
    var sketchContent: List<String>? = null

    init { readSketch() }

    fun readSketch() {
        val sketchStringUri = sharedPreferences.getString(sketchFileUriKey, null)
        if (sketchStringUri != null) {
            sketchFileUri = sketchStringUri.toUri()
            if (sketchFileUri != null) {
                val inputStream = globalContext.contentResolver.openInputStream(sketchFileUri!!)
                sketchContent = inputStream?.bufferedReader().use { it?.readLines() }
            }
        }
    }

    fun writeSketch(content: List<String>?, uri: Uri?) {
        if (content != null) {
            sketchContent = content
            sharedPreferences.edit {
                putString(sketchFileUriKey, uri.toString())
            }
        }
    }

    fun deleteSketch() {
        sketchFileUri = null
        sketchContent = null
        sharedPreferences.edit {
            remove(sketchFileUriKey)
        }
    }
}