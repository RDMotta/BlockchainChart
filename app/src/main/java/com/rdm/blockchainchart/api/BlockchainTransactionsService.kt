package com.rdm.blockchainchart.api

import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import retrofit2.http.GET
import retrofit2.Call;
import retrofit2.http.Query;

interface BlockchainTransactionsService {
    @GET("/charts/transactions-per-second")
    fun getBlockchainTransactions(
        @Query("timespan") query: String,
        @Query("rollingAverage") author: String
    ): Call<BlockchainTransactionsResponse>
}