package com.example.moodbloom.presentation

import android.app.Application
import com.example.moodbloom.data.preferences.DataStoreManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        DataStoreManager.init(this)
        FirebaseApp.initializeApp(this)
    }

}

