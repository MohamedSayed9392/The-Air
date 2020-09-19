package com.mohamedsayed.theair

import android.app.Application
import android.content.pm.ApplicationInfo
import com.mohamedsayed.theair.koin.appModule
import com.mohamedsayed.theair.koin.dbModule
import com.mohamedsayed.theair.koin.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class TheAirApplication : Application() {

    private val TAG = "TheAirApplication"

    companion object {
        lateinit var instance: TheAirApplication
        var isDebuggable = false
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        isDebuggable = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

        startKoin {
            androidLogger()
            modules(getModules())
            androidContext(instance)
        }
    }

    private fun getModules(): List<Module> {
        return listOf(viewModel, appModule, dbModule)
    }

}
