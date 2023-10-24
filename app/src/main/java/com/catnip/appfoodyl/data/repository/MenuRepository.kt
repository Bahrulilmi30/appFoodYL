package com.catnip.appfoodyl.data.repository

import com.catnip.appfoodyl.data.model.Category
import com.catnip.appfoodyl.data.model.Menu
import com.catnip.appfoodyl.data.network.api.datasource.ProductDataSource
import com.catnip.appfoodyl.utils.ResultWrapper
import com.catnip.appfoodyl.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
    fun getMenus(category: String? = null): Flow<ResultWrapper<List<Menu>>>
}
class MenuRepositoryImpl(
    private val apiDataSource: ProductDataSource
) : MenuRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getMenus(category: String?): Flow<ResultWrapper<List<Menu>>> {
        TODO("Not yet implemented")
    }

}