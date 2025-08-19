package com.example.arduinoide.data

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

// https://deepwiki.com/arduino/arduino-cli/4-grpc-api
class ArduinoCLI {
    init {
        // check if arduino-cli is installed, otherwise install it
        // https://stackoverflow.com/questions/3350283/any-way-to-run-shell-commands-on-android-programmatically
        val canSKipInstallation = isArduinoCLIInstalledOnDevice()
        if (!canSKipInstallation) {
            installArduinoCLI()
        }
    }

    fun isArduinoCLIInstalledOnDevice(): Boolean {
        val cmd = "test -d bin/arduino-cli && echo \"OK\"" // good option is "type -t bin/arduino-cli". If cli is stores, will return "file"

        var isArduinoCLIFound = false

        try {
            val process = Runtime.getRuntime().exec(cmd)
            BufferedReader(InputStreamReader(process.inputStream)).forEachLine {
                if (it == "OK") {
                    isArduinoCLIFound = true
                }
            }
        } catch (e: InterruptedException) {
            Log.w(this::class.java.canonicalName, "Cannot execute command [$cmd].", e)
        } catch (e: Exception) {
            Log.e(this::class.java.canonicalName, "Cannot execute command [$cmd].", e)
        }

        return isArduinoCLIFound
    }

    fun installArduinoCLI(): Boolean {
        // https://stackoverflow.com/a/35144372
        // https://www.networkworld.com/article/967046/how-to-identify-shell-builtins-aliases-and-executable-files-on-linux-systems.html
        val cmdInstallCurl = arrayOf("apt", "install", "curl")
        val cmdInstallCli = "curl -fsSL https://raw.githubusercontent.com/arduino/arduino-cli/master/install.sh | sh\n"

        var isArduinoCLIInstalled = false

        try {
            Runtime.getRuntime().exec(cmdInstallCurl)
            val process = Runtime.getRuntime().exec(cmdInstallCli)
            BufferedReader(InputStreamReader(process.inputStream)).forEachLine {
                Log.i(this::class.java.canonicalName, it)
            }
            isArduinoCLIInstalled = true
        } catch (e: InterruptedException) {
            Log.w(this::class.java.canonicalName, "Cannot execute command [$cmdInstallCurl | $cmdInstallCli].", e)
        } catch (e: Exception) {
            Log.e(this::class.java.canonicalName, "Cannot execute command [$cmdInstallCurl | $cmdInstallCli].", e)
        }

        return isArduinoCLIInstalled
    }

    suspend fun compile() {
        // use ArduinoCoreService.compile()
    }
}