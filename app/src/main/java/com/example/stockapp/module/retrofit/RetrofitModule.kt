package com.example.stockapp.module.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object RetrofitModule {
    private lateinit var okHttpClient: OkHttpClient
    const val BASE_URL = "https://openapi.twse.com.tw/"
    const val BWIBBU_ALL = "v1/exchangeReport/BWIBBU_ALL"
    const val STOCK_DAY_AVG_ALL = "v1/exchangeReport/STOCK_DAY_AVG_ALL"
    const val STOCK_DAY_ALL = "v1/exchangeReport/STOCK_DAY_ALL"

    @Single
    fun getInstance(): IRetrofitApi {
        val TIMEOUT_IN_SECONDS = 30L
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okhttpClientBuilder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logging)

        okHttpClient = okhttpClientBuilder.build()
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RetrofitFlowFactory.create())
            .build()

        return retrofit.create(IRetrofitApi::class.java)
    }
}