package com.example.arduinoide.service_locator

import android.content.Context
import android.content.SharedPreferences
import com.example.arduinoide.data.ArduinoStorage
import com.example.arduinoide.service.ArduinoService

class ServiceLocator(private val context: Context) {
    private lateinit var preferences: SharedPreferences
    private lateinit var storage: ArduinoStorage
    private lateinit var service: ArduinoService
    fun getPreferences(): SharedPreferences {
        if (!::preferences.isInitialized) {
            preferences = context.getSharedPreferences("arduino_ide", 0)
        }
        return preferences
    }
    fun getStorage(): ArduinoStorage {
        if (!::storage.isInitialized) {
            storage = ArduinoStorage(getPreferences())
        }
        return storage
    }
    fun getService(): ArduinoService {
        if (!::service.isInitialized) {
            service = ArduinoService()
        }
        return service
    }
}
