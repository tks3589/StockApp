package com.example.stockapp.view.viewmodel.impl

import com.example.stockapp.data.bean.StockTotalBean
import com.example.stockapp.repositroy.UiState
import kotlinx.coroutines.flow.StateFlow

interface IStockViewModel {
    val stockDataState: StateFlow<UiState<List<StockTotalBean>>>
    fun requestData()
    fun sortData(isAscending: Boolean)
}