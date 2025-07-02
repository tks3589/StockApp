package com.example.stockapp.app.model.repository.api.impl

import com.example.stockapp.app.model.data.bean.BwibbuAllBean
import com.example.stockapp.app.model.data.bean.StockDayAllBean
import com.example.stockapp.app.model.data.bean.StockDayAvgAllBean
import com.example.stockapp.app.model.data.responsevo.toBwibbuAllList
import com.example.stockapp.app.model.data.responsevo.toStockDayAllList
import com.example.stockapp.app.model.data.responsevo.toStockDayAvgAllList
import com.example.stockapp.app.model.repository.api.IStockApiModel
import com.example.stockapp.module.retrofit.IRetrofitApi
import com.example.stockapp.module.retrofit.RetrofitModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Factory(binds = [IStockApiModel::class])
class StockApiModel: IStockApiModel, KoinComponent {
    private val apiModel: IRetrofitApi by inject()

    override fun requestBwibbuAll(): Flow<List<BwibbuAllBean>> {
        val url = "${RetrofitModule.BASE_URL}${RetrofitModule.BWIBBU_ALL}"
        return apiModel.requestBwibbuAll(url).map { vo ->
            vo.toBwibbuAllList()
        }
    }

    override fun requestStockDayAvgAll(): Flow<List<StockDayAvgAllBean>> {
        val url = "${RetrofitModule.BASE_URL}${RetrofitModule.STOCK_DAY_AVG_ALL}"
        return apiModel.requestStockDayAvgAll(url).map { vo ->
            vo.toStockDayAvgAllList()
        }
    }

    override fun requestStockDayAll(): Flow<List<StockDayAllBean>> {
        val url = "${RetrofitModule.BASE_URL}${RetrofitModule.STOCK_DAY_ALL}"
        return apiModel.requestStockDayAll(url).map { vo ->
            vo.toStockDayAllList()
        }
    }
}