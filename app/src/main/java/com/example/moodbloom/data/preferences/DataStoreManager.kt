package com.example.moodbloom.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import java.io.File

object DataStoreManager {
    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.applicationContext.createDataStore(name = "MoodBloom")
    }

    fun getInstance(): DataStore<Preferences> {
        if (!::dataStore.isInitialized) {
            throw IllegalStateException("DataStoreManager not initialized!")
        }
        return dataStore
    }
}

// Extension Function
fun Context.createDataStore(name: String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        produceFile = { File(this.filesDir, "datastore/$name.preferences_pb") }
    )
}
