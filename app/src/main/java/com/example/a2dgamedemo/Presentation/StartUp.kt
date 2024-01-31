package com.example.a2dgamedemo.Presentation

import android.app.Application
import android.content.Context

class StartUp: Application() {
    override fun onCreate() {
        super.onCreate()

        ServiceRegistration.register(this)
    }

    fun getContext() : Context = applicationContext
}