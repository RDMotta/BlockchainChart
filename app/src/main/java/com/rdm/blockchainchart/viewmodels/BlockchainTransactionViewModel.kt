package com.rdm.blockchainchart.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Context
import com.rdm.blockchainchart.model.BlockchainChartUpdate
import com.rdm.blockchainchart.model.BlockchainTransactionsResponse
import com.rdm.blockchainchart.model.PointValue
import com.rdm.blockchainchart.model.TransactionCoin
import com.rdm.blockchainchart.repositories.BlockchainTransactionsRepository

class BlockchainTransactionViewModel(application: Application) : AndroidViewModel(application) {
    private var blockchainTransactionsRepository: BlockchainTransactionsRepository = BlockchainTransactionsRepository()
    private var blockchainTransactionsResponseLiveData = blockchainTransactionsRepository.getBlockchainTransactionsResponseLiveData()
    var blockchainChartUpdate: BlockchainChartUpdate? =null

    fun searchBlockchainTransactions(timespan: String, rollingAverage: String) {
        blockchainTransactionsRepository.blockchainChartUpdate = blockchainChartUpdate
        blockchainTransactionsRepository?.searchBlockchainTransactions(timespan, rollingAverage)
    }

    fun saveDataDb(context: Context, itens: List<PointValue>){
        blockchainTransactionsRepository.blockchainChartUpdate = blockchainChartUpdate
        blockchainTransactionsRepository.saveDataDb(context, itens)
    }

    fun getLastBlockchainTransactionsDb(context: Context): List<TransactionCoin>{
       return blockchainTransactionsRepository.getLastBlockchainTransactionsDb(context)
    }

    fun getBlockchainTransactionsResponseLiveData(): LiveData<BlockchainTransactionsResponse?>? {
        return blockchainTransactionsResponseLiveData
    }

}