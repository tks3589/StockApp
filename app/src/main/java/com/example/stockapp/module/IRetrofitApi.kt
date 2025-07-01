package com.example.stockapp.module

import com.example.stockapp.data.responsevo.BwibbuAllRespVo
import com.example.stockapp.data.responsevo.StockDayAllRespVo
import com.example.stockapp.data.responsevo.StockDayAvgAllRespVo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Url

interface IRetrofitApi {
    @GET
    fun requestBwibbuAll(@Url url: String): Flow<List<BwibbuAllRespVo>>

    @GET
    fun requestStockDayAvgAll(@Url url: String): Flow<List<StockDayAvgAllRespVo>>

    @GET
    fun requestStockDayAll(@Url url: String): Flow<List<StockDayAllRespVo>>
}