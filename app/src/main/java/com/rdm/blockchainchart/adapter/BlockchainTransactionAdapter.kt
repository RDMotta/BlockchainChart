package com.rdm.blockchainchart.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rdm.blockchainchart.R
import com.rdm.blockchainchart.model.TransactionCoin
import kotlinx.android.synthetic.main.card_transaction_coin.view.*
import kotlinx.android.synthetic.main.transactiondata_itemview.view.*

class BlockchainTransactionAdapter(private val dataSet: List<TransactionCoin>?) :
    RecyclerView.Adapter<BlockchainTransactionAdapter.BlockchainTransactionViewHolder>() {

    class BlockchainTransactionViewHolder(val viewData: View) : RecyclerView.ViewHolder(viewData)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BlockchainTransactionAdapter.BlockchainTransactionViewHolder {
        val viewData = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_transaction_coin, parent, false)

        return BlockchainTransactionViewHolder(viewData)
    }
    override fun onBindViewHolder(holder: BlockchainTransactionViewHolder, position: Int) {
        holder.viewData.tvDataValue.text = dataSet?.get(position)?.period
        holder.viewData.tvValue.text = dataSet?.get(position)?.valueCoin?.toString()
    }

    override fun getItemCount(): Int{
        return if (dataSet == null) 0 else dataSet?.size
    }
}