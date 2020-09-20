package com.rdm.blockchainchart.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.rdm.blockchainchart.api.BlockchainTransactionsService
import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class BlockchainTransactionsRepository{

    private val BASE_URL = "https://api.blockchain.info/"
    private var blockchainTransactionsService: BlockchainTransactionsService? = null
    private var blockchainTransactionsResponseLiveData: MutableLiveData<BlockchainTransactionsResponse>? = null

    fun init() {
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
            .create(blockchainTransactionsService!!::class.java)
        Log.d("BlockchainTransactionsRepository","Init classe BlockchainTransactionsRepository")
    }

    //timespan=5weeks&rollingAverage=8hours&format=json
    fun searchBlockchainTransactions(timespan: String, rollingAverage: String) {
    blockchainTransactionsService?.getBlockchainTransactions(timespan, rollingAverage)
            ?.enqueue(object : Callback<BlockchainTransactionsResponse?> {
                override fun onResponse(
                    call: Call<BlockchainTransactionsResponse?>?,
                    response: Response<BlockchainTransactionsResponse?>
                ) {
                    if (response.body() != null) {
                        blockchainTransactionsResponseLiveData?.postValue(response.body())
                    }
                }

                override fun onFailure(
                    call: Call<BlockchainTransactionsResponse?>?,
                    t: Throwable?
                ) {
                    blockchainTransactionsResponseLiveData?.postValue(null)
                }
            })
    }

    fun getBlockchainTransactionsResponseLiveData(): LiveData<BlockchainTransactionsResponse?>? {
        return blockchainTransactionsResponseLiveData
    }
}