package com.rdm.blockchainchart.model

import com.google.gson.annotations.SerializedName

data class PointValue (
    @SerializedName("x") val x : Int,
    @SerializedName("y") val y : Double
)