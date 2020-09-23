package com.rdm.blockchainchart.model

import retrofit2.Response

interface BlockchainChartUpdate {
    fun updateDataChat(response: Response<BlockchainTransactionsResponse?>?)
    fun saveDataDb(itens: List<PointValue>)
}