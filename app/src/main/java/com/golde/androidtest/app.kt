package com.golde.androidtest

import android.app.Application
import com.golde.androidtest.DI.appModule
import khangtran.preferenceshelper.PrefHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class app : Application() {
    override fun onCreate() {
        super.onCreate()
        /**
         * Initialize SharedPreference helper library
         * Initialize Koin
         */
        PrefHelper.initHelper(this)
        startKoin {
            androidContext(this@app)
            modules(appModule)
            androidLogger()
        }
    }
}