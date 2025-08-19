package com.example.arduinoide.data.cli.daemon

// arduino-ide-extension/src/node/arduino-daemon-impl.ts
class ArduinoDaemon {

    var _isRunning: Boolean = false
    var _port: Int? = null

    // where to start the daemon? Injection or on demand + dispose?
    suspend fun start(): Unit {
        // dispose of previous daemon + _isRunning = false

        // spawn daemon
        // get spawn arguments
        // get cliPath
        // start actual daemon as a child process
        // if daemon running: _isRunning = true
    }

    fun tryGetPort(): Int? {
        if (this._isRunning) {
            return this._port
        }
        return null
    }

}