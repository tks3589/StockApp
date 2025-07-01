package com.example.stockapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockapp.data.bean.StockTotalBean
import com.example.stockapp.repositroy.UiState
import com.example.stockapp.view.viewmodel.impl.IStockViewModel

@Composable
fun StockScreen(viewModel: IStockViewModel) {
    val stockDataState =  viewModel.stockDataState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.requestData()
    }

    when (val state = stockDataState.value) {
        is UiState.Empty -> {
            Text(text = "No data available", color = Color.Gray)
        }
        is UiState.Error -> {
            Text(text = "Error: ${state.exception}", color = Color.Red)
        }
        is UiState.Success -> {
           StockList(list = state.data)
        }
        else -> {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun StockList(list: List<StockTotalBean>) {
    LazyColumn {
        items(list) { stock ->
            StockItem(bean = stock)
        }
    }
}

@Composable
fun StockItem(bean: StockTotalBean){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .padding(12.dp)
    ) {
        Column {
            Text(text = bean.code, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = bean.name, fontSize = 16.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = "開盤價:", modifier = Modifier.weight(1f))
            Text(text = bean.openingPrice, modifier = Modifier.weight(1f))
            Text(text = "收盤價:", modifier = Modifier.weight(1f))
            Text(text = bean.closingPrice, modifier = Modifier.weight(1f))
        }
        Row {
            Text(text = "最高價:", modifier = Modifier.weight(1f))
            Text(text = bean.highestPrice, modifier = Modifier.weight(1f))
            Text(text = "最低價:", modifier = Modifier.weight(1f))
            Text(text = bean.lowestPrice, modifier = Modifier.weight(1f))
        }
        Row {
            Text(text = "漲跌價差:", modifier = Modifier.weight(1f))
            Text(text = bean.change, modifier = Modifier.weight(1f))
            Text(text = "月平均價:", modifier = Modifier.weight(1f))
            Text(text = bean.monthlyAveragePrice, modifier = Modifier.weight(1f))
        }
        Row {
            Text(text = "成交筆數:",  fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = bean.transaction, fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = "成交股數:", fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = bean.tradeVolume, fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = "成交金額:", fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = bean.tradeValue, fontSize = 10.sp, modifier = Modifier.weight(1f))
        }
    }
}