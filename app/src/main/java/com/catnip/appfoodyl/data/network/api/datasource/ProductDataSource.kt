package com.catnip.appfoodyl.data.network.api.datasource

import com.catnip.appfoodyl.data.network.api.model.category.CategoryResponse
import com.catnip.appfoodyl.data.network.api.model.menu.MenuResponse
import com.catnip.appfoodyl.data.network.api.model.order.OrderRequest
import com.catnip.appfoodyl.data.network.api.model.order.OrderResponse
import com.catnip.appfoodyl.data.network.firebase.data.ProductResponse
import com.catnip.appfoodyl.data.network.service.ProductService

interface ProductDataSource{
    suspend fun getProducts(category: String?= null) : ProductResponse

    suspend fun getCategories() : CategoryResponse

    suspend fun createOrder(orderRequest: OrderRequest) : OrderResponse

}

class ProductApiDataSource(
    private val service: ProductService
): ProductDataSource {

    override suspend fun getProducts(category: String?): ProductResponse {
        return  service.getProduct(category)
    }

    override suspend fun getCategories(): CategoryResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }

}