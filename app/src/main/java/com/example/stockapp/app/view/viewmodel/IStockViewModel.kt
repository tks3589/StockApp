package com.example.stockapp.app.view.viewmodel

import com.example.stockapp.app.model.data.bean.StockTotalBean
import com.example.stockapp.app.model.state.UiState
import kotlinx.coroutines.flow.StateFlow

interface IStockViewModel {
    val stockDataState: StateFlow<UiState<List<StockTotalBean>>>
    fun requestData()
    fun sortData(isAscending: Boolean)
}