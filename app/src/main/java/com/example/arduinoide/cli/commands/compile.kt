package com.example.arduinoide.cli.commands

import protocol_buffers.Compile
import protocol_buffers.CompileRequest

// https://github.com/arduino/arduino-cli/blob/master/commands/service_compile.go
// CompilerServerToStreams creates a gRPC CompileServer that sends the responses to the provided streams.
// The returned callback function can be used to retrieve the builder result after the compilation is done.

fun compile(req: CompileRequest) {}