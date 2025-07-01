package com.example.stockapp.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockDayAvgAllBean(
    var date: String,
    var code: String,
    var name: String,
    var closingPrice: String,
    var monthlyAveragePrice: String
): Parcelable