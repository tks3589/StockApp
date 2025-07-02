package com.example.stockapp.app.model.repository.api

import com.example.stockapp.app.model.data.bean.BwibbuAllBean
import com.example.stockapp.app.model.data.bean.StockDayAllBean
import com.example.stockapp.app.model.data.bean.StockDayAvgAllBean
import kotlinx.coroutines.flow.Flow

interface IStockApiModel {
    fun requestBwibbuAll(): Flow<List<BwibbuAllBean>>
    fun requestStockDayAvgAll(): Flow<List<StockDayAvgAllBean>>
    fun requestStockDayAll(): Flow<List<StockDayAllBean>>
}