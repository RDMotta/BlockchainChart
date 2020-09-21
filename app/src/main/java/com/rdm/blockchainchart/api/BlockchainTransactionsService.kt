package com.rdm.blockchainchart.api

import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import retrofit2.http.GET
import retrofit2.Call;
import retrofit2.http.Query;

interface BlockchainTransactionsService {
    @GET("/charts/transactions-per-second")
    fun getBlockchainTransactions(
        @Query("timespan") timespan: String,
        @Query("rollingAverage") rollingAverage: String
    ): Call<BlockchainTransactionsResponse>
}