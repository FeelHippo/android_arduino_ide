package com.example.arduinoide.data.cli.commands

import protocol_buffers.Compile
import java.util.stream.Stream

fun compile() {
    // define request
    // /repos/arduino-cli/internal/cli/compile/compile.go:231
    val compileRequest: Compile.CompileRequest
    // define stream
    var compileResponse: Compile.CompileResponse? = null // remove null
    val stream: Stream<Compile.CompileResponse> // on data => compileResponse = data
    // define builder callback
    fun builderCallback(): Compile.CompileResponse? {
        return compileResponse
    }
    // define sketch builder (data struct with all bits and pieces necessary):
    // ctx == stream, sk == type Sketch struct, see if anything else is necessary
    // /repos/arduino-cli/commands/service_compile.go:257

    // perform the actual build
    // /repos/arduino-cli/commands/service_compile.go:377
    // /repos/arduino-cli/internal/arduino/builder/builder.go:394
    // perform first three recipes: prebuild | buildSketch (no sub folders) | postbuild

    // "buildSketch" runs the following:
    // /repos/arduino-cli/internal/arduino/builder/compilation.go:32

    // if everything goes well, log success
}