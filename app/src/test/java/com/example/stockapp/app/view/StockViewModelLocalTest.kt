package com.example.stockapp.app.view

import com.example.stockapp.app.extensions.MainCoroutineScopeKotestExtension
import com.example.stockapp.app.model.data.bean.BwibbuAllBean
import com.example.stockapp.app.model.data.bean.StockDayAllBean
import com.example.stockapp.app.model.data.bean.StockDayAvgAllBean
import com.example.stockapp.app.model.data.bean.StockTotalBean
import com.example.stockapp.app.model.state.UiState
import com.example.stockapp.app.model.repository.api.IStockApiModel
import com.example.stockapp.app.view.viewmodel.impl.StockViewModel
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.koin.KoinExtension
import io.kotest.koin.KoinLifecycleMode
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.koin.dsl.module

class StockViewModelLocalTest : FeatureSpec() {
    private val mockApiModel: IStockApiModel = mockk(relaxed = true)

    private val koinTestModule = module {
        factory { mockApiModel }
    }

    override fun extensions(): List<Extension> = listOf(
        KoinExtension(module = koinTestModule, mode = KoinLifecycleMode.Root),
        MainCoroutineScopeKotestExtension()
    )


    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("Get Stock List") {
            scenario("Get list success") {
                val bwibbuList = listOf(
                    BwibbuAllBean(date = "20250701", code = "2330", name = "台積電", peRatio = "10.0", pbRatio = "1.2", dividendYield = "2.3")
                )
                val avgList = listOf(
                    StockDayAvgAllBean(date = "20250701", code = "2330", name = "台積電", closingPrice = "60.0", monthlyAveragePrice = "680.0")
                )
                val dayList = listOf(
                    StockDayAllBean(
                        date = "20250701", code = "2330", name = "台積電",
                        closingPrice = "690", openingPrice = "680", highestPrice = "695",
                        lowestPrice = "678", change = "+10", transaction = "123", tradeVolume = "1000", tradeValue = "690000"
                    )
                )

                every { mockApiModel.requestBwibbuAll() } returns flowOf(bwibbuList)
                every { mockApiModel.requestStockDayAvgAll() } returns flowOf(avgList)
                every { mockApiModel.requestStockDayAll() } returns flowOf(dayList)

                val viewModel = StockViewModel()
                viewModel.requestData()

                viewModel.stockDataState.value shouldBe UiState.Success(
                    listOf(
                        StockTotalBean(
                            date = "20250701",
                            code = "2330",
                            name = "台積電",
                            peRatio = "10.0",
                            pbRatio = "1.2",
                            dividendYield = "2.3",
                            closingPrice = "690",
                            monthlyAveragePrice = "680.0",
                            tradeVolume = "1000",
                            tradeValue = "690000",
                            openingPrice = "680",
                            highestPrice = "695",
                            lowestPrice = "678",
                            change = "+10",
                            transaction = "123"
                        )
                    )
                )
            }

            scenario("Get list fail") {
                val error = Throwable("fail!")
                every { mockApiModel.requestBwibbuAll() } returns flow { throw error }
                every { mockApiModel.requestStockDayAvgAll() } returns flowOf(emptyList())
                every { mockApiModel.requestStockDayAll() } returns flowOf(emptyList())
                val viewModel = StockViewModel()
                viewModel.requestData()
                viewModel.stockDataState.value shouldBe UiState.Error(error.message)
            }
        }

        feature("Sort Stock List") {
            val unsortedList = listOf(
                StockTotalBean(
                    code = "3000", name = "股票C", date = "20250701",
                    peRatio = "12.0", dividendYield = "2.5", pbRatio = "1.8",
                    closingPrice = "110", monthlyAveragePrice = "105",
                    tradeVolume = "100", tradeValue = "110000",
                    openingPrice = "100", highestPrice = "115",
                    lowestPrice = "98", change = "+10", transaction = "80"
                ),
                StockTotalBean(
                    code = "1000", name = "股票A", date = "20250701",
                    peRatio = "10.0", dividendYield = "3.0", pbRatio = "1.2",
                    closingPrice = "60", monthlyAveragePrice = "55",
                    tradeVolume = "200", tradeValue = "60000",
                    openingPrice = "50", highestPrice = "65",
                    lowestPrice = "48", change = "+10", transaction = "160"
                )
            )

            fun assertSorted(viewModel: StockViewModel, expectedCodes: List<String>) {
                val actual = (viewModel.stockDataState.value as? UiState.Success)?.data?.map { it.code }
                actual shouldBe expectedCodes
            }

            scenario("Sort by ascending") {
                val viewModel = StockViewModel()
                viewModel.stockDataState.value = UiState.Success(unsortedList)
                viewModel.sortData(isAscending = true)
                assertSorted(viewModel, listOf("1000", "3000"))
            }

            scenario("Sort by descending") {
                val viewModel = StockViewModel()
                viewModel.stockDataState.value = UiState.Success(unsortedList)
                viewModel.sortData(isAscending = false)
                assertSorted(viewModel, listOf("3000", "1000"))
            }
        }
    }
}