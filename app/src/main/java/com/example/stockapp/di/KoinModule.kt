package com.example.stockapp.di

import com.example.stockapp.KSPKoinModule
import com.example.stockapp.module.retrofit.RetrofitModule
import org.koin.ksp.generated.module

fun getModuleList(): List<org.koin.core.module.Module> {
    return listOf(
        RetrofitModule.module,
        KSPKoinModule().module
    )
}