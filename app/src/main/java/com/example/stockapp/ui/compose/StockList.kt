package com.example.stockapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockapp.R
import com.example.stockapp.data.bean.StockTotalBean
import com.example.stockapp.repositroy.UiState
import com.example.stockapp.util.PriceUtil.isGreaterThanPrice
import com.example.stockapp.util.PriceUtil.isGreaterThanZero
import com.example.stockapp.view.viewmodel.impl.IStockViewModel

@Composable
fun StockScreen(viewModel: IStockViewModel) {
    val stockDataState = viewModel.stockDataState.collectAsState()
    val showDialog = remember { mutableStateOf<StockTotalBean?>(null) }

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
            LazyColumn {
                items(state.data) { stock ->
                    StockItem(bean = stock, showDialog = showDialog)
                }
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }

    showDialog.value?.let { bean ->
        StockDialog(bean = bean, showDialog = showDialog)
    }
}

@Composable
fun StockItem(bean: StockTotalBean, showDialog: MutableState<StockTotalBean?>){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
            .padding(12.dp)
            .clickable { showDialog.value = bean }
    ) {
        Column {
            Text(text = bean.code, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = bean.name, fontSize = 16.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = stringResource(R.string.opening_price), modifier = Modifier.weight(1f))
            Text(text = bean.openingPrice, modifier = Modifier.weight(1f))
            Text(text =stringResource(R.string.closing_price), modifier = Modifier.weight(1f))
            Text(text = bean.closingPrice, modifier = Modifier.weight(1f),
                color = if (bean.closingPrice.isGreaterThanPrice(bean.monthlyAveragePrice)) {
                    Color.Red
                } else {
                    Color.Green
                }
            )
        }
        Row {
            Text(text = stringResource(R.string.highest_price), modifier = Modifier.weight(1f))
            Text(text = bean.highestPrice, modifier = Modifier.weight(1f))
            Text(text = stringResource(R.string.lowest_price), modifier = Modifier.weight(1f))
            Text(text = bean.lowestPrice, modifier = Modifier.weight(1f))
        }
        Row {
            Text(text = stringResource(R.string.change), modifier = Modifier.weight(1f))
            Text(text = bean.change, modifier = Modifier.weight(1f),
                color = if (bean.change.isGreaterThanZero()) {
                    Color.Red
                } else {
                    Color.Green
                }
            )
            Text(text = stringResource(R.string.monthly_average_price), modifier = Modifier.weight(1f))
            Text(text = bean.monthlyAveragePrice, modifier = Modifier.weight(1f))
        }
        Row {
            Text(text = stringResource(R.string.transaction),  fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = bean.transaction, fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = stringResource(R.string.trade_volume), fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = bean.tradeVolume, fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text =stringResource(R.string.trade_value), fontSize = 10.sp, modifier = Modifier.weight(1f))
            Text(text = bean.tradeValue, fontSize = 10.sp, modifier = Modifier.weight(1f))
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDialog(bean: StockTotalBean, showDialog: MutableState<StockTotalBean?>) {
    BasicAlertDialog(
        onDismissRequest = {
            showDialog.value = null
        }
    ) {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "${stringResource(R.string.peratio_value)} ${bean.peRatio}")
                Text(text = "${stringResource(R.string.dividend_value)} ${bean.dividendYield}")
                Text(text = "${stringResource(R.string.pbratio_value)} ${bean.pbRatio}")
            }
        }
    }
}