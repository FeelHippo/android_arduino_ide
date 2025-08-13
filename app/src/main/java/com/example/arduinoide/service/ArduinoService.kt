package com.example.arduinoide.service

class ArduinoService {
    fun verifySketch(content: List<String>?) {
        // https://github.com/arduino/arduino-ide/blob/3d8f3fa3e39d5ecad57ecd5d5901167ddb7450ab/arduino-ide-extension/src/browser/contributions/verify-sketch.ts#L178
    }
    fun uploadSketch(content: List<String>?) {
        verifySketch(content)
        // ...
    }
}