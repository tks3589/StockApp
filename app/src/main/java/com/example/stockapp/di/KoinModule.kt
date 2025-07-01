package com.example.stockapp.di

import com.example.stockapp.KoinModule
import com.example.stockapp.module.RetrofitModule
import org.koin.ksp.generated.module

fun getModuleList(): List<org.koin.core.module.Module> {
    return listOf(
        RetrofitModule.module,
        KoinModule().module
    )
}