package com.example.stockapp.app.model.data.bean

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