package com.catnip.appfoodyl.data.network.firebase.data


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ProductResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<ItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)