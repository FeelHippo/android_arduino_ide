package com.example.arduinoide.service

import com.example.arduinoide.data.ArduinoCLI

class ArduinoService {

    private var cli: ArduinoCLI = ArduinoCLI()
    fun verifySketch(content: List<String>?) {
        // https://github.com/arduino/arduino-ide/blob/3d8f3fa3e39d5ecad57ecd5d5901167ddb7450ab/arduino-ide-extension/src/browser/contributions/verify-sketch.ts#L178
        // use ArduinoCLI => Compile
        // cli.compile()
    }
    fun uploadSketch(content: List<String>?) {
        // ...
    }
}