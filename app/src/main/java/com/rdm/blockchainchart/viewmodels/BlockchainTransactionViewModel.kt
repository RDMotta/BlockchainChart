package com.rdm.blockchainchart.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.rdm.blockchainchart.model.BlockchainChartUpdate
import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import com.rdm.blockchainchart.repositories.BlockchainTransactionsRepository

class BlockchainTransactionViewModel(application: Application) : AndroidViewModel(application) {
    private var blockchainTransactionsRepository: BlockchainTransactionsRepository = BlockchainTransactionsRepository()
    private var blockchainTransactionsResponseLiveData = blockchainTransactionsRepository.getBlockchainTransactionsResponseLiveData()
    var blockchainChartUpdate: BlockchainChartUpdate? =null

    fun searchBlockchainTransactions(timespan: String, rollingAverage: String) {
        blockchainTransactionsRepository.blockchainChartUpdate = blockchainChartUpdate
        blockchainTransactionsRepository?.searchBlockchainTransactions(timespan, rollingAverage)
    }

    fun getRepository(): BlockchainTransactionsRepository{
        blockchainTransactionsRepository.blockchainChartUpdate = blockchainChartUpdate
        return blockchainTransactionsRepository
    }

    fun getBlockchainTransactionsResponseLiveData(): LiveData<BlockchainTransactionsResponse?>? {
        return blockchainTransactionsResponseLiveData
    }

}