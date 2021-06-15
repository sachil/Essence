package xyz.sachil.essence

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import xyz.sachil.essence.util.appModules
import xyz.sachil.essence.util.frameworkModules

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //使用koin进行注入依赖
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModules, frameworkModules)
        }
    }
}