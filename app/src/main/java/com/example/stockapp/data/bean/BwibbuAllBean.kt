package com.example.stockapp.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BwibbuAllBean(
    var date: String,
    var code: String,
    var name: String,
    var peRatio: String,
    var dividendYield: String,
    var pbRatio: String
) : Parcelable