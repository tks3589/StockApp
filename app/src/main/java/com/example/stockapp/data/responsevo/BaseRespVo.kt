package com.example.stockapp.data.responsevo

import com.google.gson.annotations.SerializedName

open class BaseRespVo {
    @SerializedName("Date")
    var date: String? = null
        get() = field.orEmpty()

    @SerializedName("Code")
    var code: String? = null
        get() = field.orEmpty()

    @SerializedName("Name")
    var name: String? = null
        get() = field.orEmpty()
}