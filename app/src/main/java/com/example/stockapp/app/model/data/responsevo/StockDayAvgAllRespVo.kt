package com.example.stockapp.app.model.data.responsevo

import com.example.stockapp.app.model.data.bean.StockDayAvgAllBean
import com.google.gson.annotations.SerializedName

class StockDayAvgAllRespVo: BaseRespVo() {
    @SerializedName("ClosingPrice")
    var closingPrice: String? = null
        get() = field.orEmpty()

    @SerializedName("MonthlyAveragePrice")
    var monthlyAveragePrice: String? = null
        get() = field.orEmpty()
}

fun List<StockDayAvgAllRespVo>.toStockDayAvgAllList(): List<StockDayAvgAllBean> {
    return this.map { vo ->
        StockDayAvgAllBean(
            date = vo.date!!,
            code = vo.code!!,
            name = vo.name!!,
            closingPrice = vo.closingPrice!!,
            monthlyAveragePrice = vo.monthlyAveragePrice!!
        )
    }
}