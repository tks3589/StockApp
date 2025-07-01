package com.example.stockapp.data.responsevo

import com.example.stockapp.data.bean.StockDayAllBean
import com.google.gson.annotations.SerializedName

class StockDayAllRespVo: BaseRespVo() {
    @SerializedName("TradeVolume")
    var tradeVolume: String? = null
        get() = field.orEmpty()

    @SerializedName("TradeValue")
    var tradeValue: String? = null
        get() = field.orEmpty()

    @SerializedName("OpeningPrice")
    var openingPrice: String? = null
        get() = field.orEmpty()

    @SerializedName("HighestPrice")
    var highestPrice: String? = null
        get() = field.orEmpty()

    @SerializedName("LowestPrice")
    var lowestPrice: String? = null
        get() = field.orEmpty()

    @SerializedName("ClosingPrice")
    var closingPrice: String? = null
        get() = field.orEmpty()

    @SerializedName("Change")
    var change: String? = null
        get() = field.orEmpty()

    @SerializedName("Transaction")
    var transaction: String? = null
        get() = field.orEmpty()
}

fun List<StockDayAllRespVo>.toStockDayAllList(): List<StockDayAllBean> {
    return this.map { vo ->
        StockDayAllBean(
            date = vo.date!!,
            code = vo.code!!,
            name = vo.name!!,
            tradeVolume = vo.tradeVolume!!,
            tradeValue = vo.tradeValue!!,
            openingPrice = vo.openingPrice!!,
            highestPrice = vo.highestPrice!!,
            lowestPrice = vo.lowestPrice!!,
            closingPrice = vo.closingPrice!!,
            change = vo.change!!,
            transaction = vo.transaction!!
        )
    }
}