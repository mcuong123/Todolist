package com.example.todolist.ui.screen

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
       Log.d("App", "onCreate")
    }
}