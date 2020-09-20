package com.rdm.blockchainchart.model

import android.arch.lifecycle.Observer
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BlockchainTransactionsResponse {
    @SerializedName("status")
    @Expose
    val status: String = ""
    @SerializedName("name")
    @Expose
    val name: String = ""
    @SerializedName("unit")
    @Expose
    val unit: String = ""
    @SerializedName("period")
    @Expose
    val period: String = "day"
    @SerializedName("description")
    @Expose
    val description: String = ""
    @SerializedName("values")
    @Expose
    val values: List<PointValue> = emptyList()
}