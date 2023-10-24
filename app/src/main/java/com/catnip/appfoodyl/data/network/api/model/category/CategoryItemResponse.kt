package com.catnip.appfoodyl.data.network.api.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.catnip.appfoodyl.data.model.Category

@Keep
data class CategoryItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imgUrlCategory")
    val imgUrlCategory: String?,
    @SerializedName("nameOfCategory")
    val nameOfCategory: String?,
    @SerializedName("slug")
    val slug: String?
)

fun CategoryResponse.toCategory() = Category(
    name = this.name.orEmpty(),
    tak = this.imageUrl.orEmpty()
)
fun Collection<CategoryResponse>.toCategoryList() = this.map { it.toCategory()}