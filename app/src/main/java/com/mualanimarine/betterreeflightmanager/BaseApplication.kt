package com.mualanimarine.betterreeflightmanager

import android.app.Application
import com.mualanimarine.betterreeflightmanager.util.SharedPreferencesUtil

class BaseApplication : Application() {
    lateinit var dataStore: SharedPreferencesUtil
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataStore = SharedPreferencesUtil.getInstance(this)
    }

    companion object {
        lateinit var instance: BaseApplication
            private set
    }
}

