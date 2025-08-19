package com.example.arduinoide.data.cli.provider

import com.example.arduinoide.data.cli.daemon.ArduinoDaemon

// arduino-ide-extension/src/node/core-client-provider.ts
class ArduinoCoreClientProvider {
    var _arduinoDaemon: ArduinoDaemon? = null
    var _arduinoCoreService: String = "" // change this, should be the missing service not compiled by protoc, NOT ArduinoCoreService.kt from this project

    init {
        val port = this._arduinoDaemon?.tryGetPort()
        if (port != null) {
            this.create(port)
        }
    }

    fun create(port: Int) {
        // create gRPC Client and assign it to private var _arduinoCoreService,
        // which should be the gRPC service "ArduinoCoreService" which is not being compiled right now
        // see arduino-ide-extension/src/node/core-client-provider.ts:416
        // ==>
        // see arduino-ide-extension/src/node/cli-protocol/cc/arduino/cli/commands/v1/commands_grpc_pb.d.ts
    }

    // get client() returns an instance of arduino core client service
}