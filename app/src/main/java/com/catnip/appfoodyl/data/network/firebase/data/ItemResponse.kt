package com.catnip.appfoodyl.data.network.firebase.data

data class ItemResponse(
    val id : Long,
    val descOfMenu: String?,
    val locationName: String?,
    val locationUrl: String?,
    val name: String?,
    val price: Double?,
    val productImgUrl: String?
)

fun ItemResponse.toProductItemResponse() = ItemResponse(
    id = this.id,
    descOfMenu = this.descOfMenu,
    locationName = this.locationName,
    locationUrl = this.locationUrl,
    name = this.name,
    price = this.price,
    productImgUrl = this.productImgUrl
)

fun Collection<ItemResponse>.toProcustItemResponse() = this.map {
    it.toProductItemResponse()
}