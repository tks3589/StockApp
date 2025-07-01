package com.example.stockapp.data.responsevo

import com.example.stockapp.data.bean.BwibbuAllBean
import com.google.gson.annotations.SerializedName

class BwibbuAllRespVo: BaseRespVo() {
    @SerializedName("PEratio")
    var peRatio: String? = null
        get() = field.orEmpty()

    @SerializedName("DividendYield")
    var dividendYield: String? = null
        get() = field.orEmpty()

    @SerializedName("PBratio")
    var pbRatio: String? = null
        get() = field.orEmpty()
}

fun List<BwibbuAllRespVo>.toBwibbuAllList(): List<BwibbuAllBean> {
    return this.map { vo ->
        BwibbuAllBean(
            date = vo.date!!,
            code = vo.code!!,
            name = vo.name!!,
            peRatio = vo.peRatio!!,
            dividendYield = vo.dividendYield!!,
            pbRatio = vo.pbRatio!!
        )
    }
}