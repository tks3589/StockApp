package com.example.stockapp.repositroy.api.impl

import com.example.stockapp.data.bean.BwibbuAllBean
import com.example.stockapp.data.bean.StockDayAllBean
import com.example.stockapp.data.bean.StockDayAvgAllBean
import kotlinx.coroutines.flow.Flow

interface IStockApiModel {
    fun requestBwibbuAll(): Flow<List<BwibbuAllBean>>
    fun requestStockDayAvgAll(): Flow<List<StockDayAvgAllBean>>
    fun requestStockDayAll(): Flow<List<StockDayAllBean>>
}