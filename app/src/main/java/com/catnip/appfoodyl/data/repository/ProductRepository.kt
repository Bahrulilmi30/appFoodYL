package com.catnip.appfoodyl.data.repository

import com.catnip.appfoodyl.data.network.firebase.data.ItemResponse
import com.catnip.appfoodyl.data.network.firebase.data.toProcustItemResponse
import com.catnip.appfoodyl.data.network.api.datasource.ProductDataSource
import com.catnip.appfoodyl.utils.ResultWrapper
import com.catnip.appfoodyl.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<ResultWrapper<List<ItemResponse>>>
}

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource
) : ProductRepository {
    override suspend fun getProducts(): Flow<ResultWrapper<List<ItemResponse>>> {
        return proceedFlow {
            dataSource.getProducts().data!!.toProcustItemResponse()
        }
    }

}
