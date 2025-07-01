package com.example.stockapp

import android.app.Application
import com.example.stockapp.di.getModuleList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StockApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@StockApplication)
            modules(getModuleList())
        }
    }
}