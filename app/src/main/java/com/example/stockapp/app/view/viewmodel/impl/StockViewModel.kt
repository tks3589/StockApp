package com.example.stockapp.app.view.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.app.model.data.bean.BwibbuAllBean
import com.example.stockapp.app.model.data.bean.StockDayAllBean
import com.example.stockapp.app.model.data.bean.StockDayAvgAllBean
import com.example.stockapp.app.model.data.bean.StockTotalBean
import com.example.stockapp.app.model.state.UiState
import com.example.stockapp.app.model.repository.api.IStockApiModel
import com.example.stockapp.app.view.viewmodel.IStockViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinViewModel
class StockViewModel: ViewModel(), IStockViewModel, KoinComponent {
    private val apiModel: IStockApiModel by inject()
    override var stockDataState: MutableStateFlow<UiState<List<StockTotalBean>>> = MutableStateFlow(
        UiState.Idle)

    override fun requestData() {
        viewModelScope.launch {
            apiModel.requestBwibbuAll().zip(
                apiModel.requestStockDayAvgAll()
            ) { bwibbuAll, stockDayAvgAll ->
                bwibbuAll to stockDayAvgAll
            }
            .zip(apiModel.requestStockDayAll()) { (bwibbuAll, stockDayAvgAll), stockDayAll ->
                mergeTotalData(
                    bwibbuAll = bwibbuAll,
                    stockDayAvgAll = stockDayAvgAll,
                    stockDayAll = stockDayAll
                )
            }.onStart {
                stockDataState.value = UiState.Loading
            }.catch {
                stockDataState.value = UiState.Error(it.message)
            }.collect {
                if (it.isEmpty()) {
                    stockDataState.value = UiState.Empty
                } else {
                    stockDataState.value = UiState.Success(it)
                }
            }
        }
    }

    override fun sortData(isAscending: Boolean) {
        (stockDataState.value as? UiState.Success<List<StockTotalBean>>)?.data?.let { data ->
            val sortedData = if (isAscending) {
                data.sortedBy { it.code }
            } else {
                data.sortedByDescending { it.code }
            }
            stockDataState.value = UiState.Success(sortedData)
        }
    }

    private fun mergeTotalData(
        bwibbuAll: List<BwibbuAllBean>,
        stockDayAvgAll: List<StockDayAvgAllBean>,
        stockDayAll: List<StockDayAllBean>
    ): List<StockTotalBean> {
        val bwibbuMap = bwibbuAll.associateBy { it.code to it.date}
        val stockDayAvgMap = stockDayAvgAll.associateBy { it.code to it.date}
        return stockDayAll.mapNotNull { day ->
            val key = day.code to day.date
            val bwibbu = bwibbuMap[key]
            val stockDayAvg = stockDayAvgMap[key]
            if (bwibbu != null && stockDayAvg != null) {
                StockTotalBean(
                    date = day.date,
                    code = day.code,
                    name = day.name,
                    peRatio = bwibbu.peRatio,
                    dividendYield = bwibbu.dividendYield,
                    pbRatio = bwibbu.pbRatio,
                    closingPrice = day.closingPrice,
                    monthlyAveragePrice = stockDayAvg.monthlyAveragePrice,
                    tradeVolume = day.tradeVolume,
                    tradeValue = day.tradeValue,
                    openingPrice = day.openingPrice,
                    highestPrice = day.highestPrice,
                    lowestPrice = day.lowestPrice,
                    change = day.change,
                    transaction = day.transaction
                )
            } else {
                null
            }
        }
    }
}