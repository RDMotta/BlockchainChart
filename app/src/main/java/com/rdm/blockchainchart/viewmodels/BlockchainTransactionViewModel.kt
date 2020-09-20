package com.rdm.blockchainchart.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import com.rdm.blockchainchart.repositories.BlockchainTransactionsRepository

class BlockchainTransactionViewModel(application: Application) : AndroidViewModel(application) {
    private var blockchainTransactionsRepository: BlockchainTransactionsRepository = BlockchainTransactionsRepository()
    private var blockchainTransactionsResponseLiveData = blockchainTransactionsRepository.getBlockchainTransactionsResponseLiveData()

    fun searchBlockchainTransactions(timespan: String, rollingAverage: String) {
        blockchainTransactionsRepository?.searchBlockchainTransactions(timespan, rollingAverage)
    }

    fun getBlockchainTransactionsResponseLiveData(): LiveData<BlockchainTransactionsResponse?>? {
        return blockchainTransactionsResponseLiveData
    }

}