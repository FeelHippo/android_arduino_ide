package com.example.arduinoide.presentation.utils

import androidx.compose.ui.graphics.Color

// TODO(Filippo): eventually add all items from  functions, values (variables and constants), and structure
val DIGITAL_ANALOG_IO = arrayOf(
    "digitalRead",
    "digitalWrite",
    "pinMode",
    "analogRead",
    "analogReadResolution",
    "analogReference",
    "analogWrite",
    "analogWriteResolution",
)

val MATH = arrayOf(
    "abs",
    "constrain",
    "map",
    "max",
    "min",
    "pow",
    "sq",
    "sqrt",
)

val BITS_AND_BYTES = arrayOf(
    "bit",
    "bitClear",
    "bitRead",
    "bitSet",
    "bitWrite",
    "highByte",
    "lowByte",
)

val TIME = arrayOf(
    "delay",
    "delayMicroseconds",
    "micros",
    "millis",
)

val RANDOM = arrayOf(
    "random",
    "randomSeed",
)

val COMMUNICATION = arrayOf(
    "SPI",
    "Print",
    "Serial",
    "Stream",
    "Wire",
    "begin",
    "println",
    "print",
)

val VARIABLES_DATA_TYPES = arrayOf(
    "array",
    "bool",
    "boolean",
    "byte",
    "char",
    "double",
    "float",
    "int",
    "long",
    "short",
    "size_t",
    "string",
    "void",
    "word",
)

val VARIABLES_SCOPE_AND_QUALIFIERS = arrayOf(
    "const",
    "scope",
    "static",
    "volatile",
)

val SKETCH = arrayOf(
    "loop",
    "setup",
)

val CONTROL_STRUCTURE = arrayOf(
    "break",
    "continue",
    "do",
    "while",
    "else",
    "for",
    "goto",
    "if",
    "return",
    "switch",
    "case",
    "while",
)

enum class ArduinoLanguageStyle(val color: Color, val commands: Array<String>) {
    ARDUINO_DIGITAL_ANALOG_IO(Color(0xff81d5c1), DIGITAL_ANALOG_IO),
    ARDUINO_MATH(Color(0xffdddccd), MATH),
    ARDUINO_BITS_AND_BYTES(Color(0xfff9f6eb), BITS_AND_BYTES),
    ARDUINO_TIME(Color(0xfff3d88e), TIME),
    ARDUINO_RANDOM(Color(0xfff3d88e), RANDOM),
    ARDUINO_COMMUNICATION(Color(0xfff3d88e), COMMUNICATION),
    ARDUINO_DATA_TYPES(Color(0xffe8bba8), VARIABLES_DATA_TYPES),
    ARDUINO_SCOPE_AND_QUALIFIERS(Color(0xffe8bba8), VARIABLES_SCOPE_AND_QUALIFIERS),
    ARDUINO_SKETCH(Color(0xfff3d88e), SKETCH),
    ARDUINO_CONTROL_STRUCTURE(Color(0xFFA5CA9C), CONTROL_STRUCTURE),
}