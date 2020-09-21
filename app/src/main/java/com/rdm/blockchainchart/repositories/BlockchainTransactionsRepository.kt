package com.rdm.blockchainchart.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.rdm.blockchainchart.api.BlockchainTransactionsService
import com.rdm.blockchainchart.model.BlockchainChartUpdate
import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class BlockchainTransactionsRepository {
    private val BASE_URL = "https://api.blockchain.info"
    private var blockchainTransactionsService: BlockchainTransactionsService? = null
    private var blockchainTransactionsResponseLiveData: MutableLiveData<BlockchainTransactionsResponse>? = null
    var blockchainChartUpdate: BlockchainChartUpdate? = null

    constructor(){
        blockchainTransactionsResponseLiveData =
            MutableLiveData()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        blockchainTransactionsService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BlockchainTransactionsService::class.java)
    }

    fun searchBlockchainTransactions(timespan: String, rollingAverage: String) {
        blockchainTransactionsService?.getBlockchainTransactions(timespan, rollingAverage)
            ?.enqueue(object : Callback<BlockchainTransactionsResponse?> {
                override fun onResponse(
                    call: Call<BlockchainTransactionsResponse?>?,
                    response: Response<BlockchainTransactionsResponse?>
                ) {
                    if (response.body() != null) {
                        blockchainTransactionsResponseLiveData?.postValue(response.body())
                        blockchainChartUpdate?.updateData(response)
                    }
                }

                override fun onFailure(
                    call: Call<BlockchainTransactionsResponse?>?,
                    t: Throwable?
                ) {
                    blockchainTransactionsResponseLiveData?.postValue(null)
                    blockchainChartUpdate?.updateData(null)
                }
            })
    }

    fun getBlockchainTransactionsResponseLiveData(): LiveData<BlockchainTransactionsResponse?>? {
        return blockchainTransactionsResponseLiveData
    }
}