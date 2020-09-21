package com.rdm.blockchainchart.model

import retrofit2.Response

interface BlockchainChartUpdate {
    fun updateData(response: Response<BlockchainTransactionsResponse?>?)
}