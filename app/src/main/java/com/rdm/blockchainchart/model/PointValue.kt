package com.rdm.blockchainchart.model

import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId

data class PointValue (
    @SerializedName("x") val x : Int,
    @SerializedName("y") val y : Double
){
    fun xToDateTimeZone(): String{
        return  Instant.ofEpochSecond(x.toLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime().toString()
    }
}