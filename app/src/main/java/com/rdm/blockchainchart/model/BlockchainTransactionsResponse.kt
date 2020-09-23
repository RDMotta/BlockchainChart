package com.rdm.blockchainchart.model

import com.google.gson.annotations.SerializedName

class BlockchainTransactionsResponse (
    @SerializedName("status") val status : String,
    @SerializedName("name") val name : String,
    @SerializedName("unit") val unit : String,
    @SerializedName("period") val period : String,
    @SerializedName("description") val description : String,
    @SerializedName("values") val values : List<PointValue>
    )