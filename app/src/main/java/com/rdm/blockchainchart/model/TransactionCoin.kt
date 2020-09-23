package com.rdm.blockchainchart.model

import com.google.gson.annotations.SerializedName

class TransactionCoin (
    @SerializedName("x") val period : String,
    @SerializedName("y") val valueCoin : Float
)