package com.rdm.blockchainchart.api

import com.rdm.blockchainchart.model.ChartResponse
import retrofit2.http.GET
import retrofit2.Call;
import retrofit2.http.Query;

class BlockchainChartService {
    @GET("/books/v1/volumes")
    fun searchChar(
        @Query("q") query: String?,
        @Query("inauthor") author: String?
    ): Call<ChartResponse?>?
}