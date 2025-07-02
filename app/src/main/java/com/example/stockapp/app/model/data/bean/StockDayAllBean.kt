package com.example.stockapp.app.model.data.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockDayAllBean(
    var date: String,
    var code: String,
    var name: String,
    var tradeVolume: String,
    var tradeValue: String,
    var openingPrice: String,
    var highestPrice: String,
    var lowestPrice: String,
    var closingPrice: String,
    var change: String,
    var transaction: String
): Parcelable