package com.example.arduinoide.data.cli.core_service

import com.example.arduinoide.data.cli.provider.ArduinoCoreClientProvider

// arduino-ide-extension/src/node/core-service-impl.ts
class ArduinoCoreService {
    // instantiate ArduinoCoreClientProvider
    val _arduinoCoreClientProvider = ArduinoCoreClientProvider()
    fun compile() {
        // start compile stream
        // ArduinoCoreClientProvider provides client + instance
        // compileRequest => arduino-ide-extension/src/node/core-service-impl.ts: 184
        // startCompileStream => arduino-ide-extension/src/node/core-service-impl.ts: 153
        // dispose
    }
}