package com.rdm.blockchainchart.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.ContentValues
import android.content.Context
import com.rdm.blockchainchart.api.BlockchainTransactionsService
import com.rdm.blockchainchart.model.BlockchainChartUpdate
import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import com.rdm.blockchainchart.model.PointValue
import com.rdm.blockchainchart.model.TransactionCoin
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.ZoneId

class BlockchainTransactionsRepository {
    private val BASE_URL = "https://api.blockchain.info"
    private var blockchainTransactionsService: BlockchainTransactionsService? = null
    private var blockchainTransactionsResponseLiveData: MutableLiveData<BlockchainTransactionsResponse>? =
        null
    var blockchainChartUpdate: BlockchainChartUpdate? = null

    constructor() {
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
                        blockchainChartUpdate?.saveDataDb(response.body()!!.values)
                        blockchainChartUpdate?.updateDataChat(response)
                    }
                }

                override fun onFailure(
                    call: Call<BlockchainTransactionsResponse?>?,
                    t: Throwable?
                ) {
                    blockchainTransactionsResponseLiveData?.postValue(null)
                    blockchainChartUpdate?.updateDataChat(null)
                }
            })
    }

    fun saveDataDb(context: Context, itens: List<PointValue>) {
        var dbManager = DBBlockchainManager(context)
        for (item in itens) {
            var values = ContentValues()
            values.put(DBBlockchainManager.period, item.xToDateTimeZone())
            values.put(DBBlockchainManager.value_coin, item.y)
            dbManager.insert(values)
        }
    }

    fun  getLastBlockchainTransactionsDb(context: Context): List<TransactionCoin>{
        var dbManager = DBBlockchainManager(context)
        val cursor = dbManager.getLast()
        var pointValues = ArrayList<TransactionCoin>()
        cursor.moveToNext()
        if (cursor.moveToFirst()) {
            do {
                val period = cursor.getString(cursor.getColumnIndex(DBBlockchainManager.period))
                val value_coin = cursor.getFloat(cursor.getColumnIndex(DBBlockchainManager.value_coin))
                pointValues.add(TransactionCoin( period, value_coin))
            } while (cursor.moveToNext())
        }
        return pointValues
    }

    fun getBlockchainTransactionsResponseLiveData(): LiveData<BlockchainTransactionsResponse?>? {
        return blockchainTransactionsResponseLiveData
    }
}