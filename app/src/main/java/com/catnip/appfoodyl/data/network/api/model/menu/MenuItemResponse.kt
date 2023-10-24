package com.catnip.appfoodyl.data.network.api.model.menu


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.catnip.appfoodyl.data.model.Menu

@Keep
data class MenuItemResponse(
    @SerializedName("descOfMenu")
    val descOfMenu: String?,
    @SerializedName("locationName")
    val locationName: String?,
    @SerializedName("locationUrl")
    val locationUrl: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("productImgUrl")
    val productImgUrl: String?
)

fun MenuItemResponse.toMenu() = Menu(
    name = this.name.orEmpty(),
    price = this.price ?: 0,
    imageUrl = this.productImgUrl.orEmpty(),
    description = this.descOfMenu.orEmpty()
)

fun Collection<MenuItemResponse>.toMenuList() = this.map { it.toMenu() }
