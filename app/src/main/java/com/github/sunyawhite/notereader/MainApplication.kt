package com.github.sunyawhite.notereader

import android.app.Application
import com.github.sunyawhite.notereader.Koin.repositoryModule
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin { repositoryModule }
    }
}