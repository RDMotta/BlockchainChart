package com.rdm.blockchainchart.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.sql.Timestamp

class PointValue (
    @SerializedName("y")
    @Expose
    val y: BigDecimal,
    @SerializedName("x")
    @Expose
    val x: Timestamp
)