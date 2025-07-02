package com.example.stockapp.app.view

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stockapp.R
import com.example.stockapp.compose.ui.StockScreen
import com.example.stockapp.compose.theme.StockAppTheme
import com.example.stockapp.app.view.viewmodel.impl.StockViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.stockapp.app.view.viewmodel.IStockViewModel

class StockActivity : AppCompatActivity() {
    private lateinit var composeView: ComposeView
    private lateinit var sortButton: ImageButton
    private val viewModel: IStockViewModel by viewModel<StockViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stock)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() {
        composeView = findViewById(R.id.composeView)
        sortButton = findViewById(R.id.imageButton)

        // Set up the Compose view and other UI elements here
        composeView.setContent {
            StockAppTheme {
                StockScreen(viewModel)
            }
        }

        sortButton.setOnClickListener {
            BottomSheetFragment.newInstance { isAscending ->
                viewModel.sortData(isAscending)
            }.show(supportFragmentManager, "SortOptions")
        }
    }
}