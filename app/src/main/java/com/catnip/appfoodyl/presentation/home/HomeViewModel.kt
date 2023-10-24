package com.catnip.appfoodyl.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.appfoodyl.data.model.Category
import com.catnip.appfoodyl.data.network.firebase.data.ItemResponse
import com.catnip.appfoodyl.data.repository.ProductRepository
import com.catnip.appfoodyl.data.repository.UserRepositoryImpl
import com.catnip.appfoodyl.datastore.UserPreferenceDataSourceImpl
import com.catnip.appfoodyl.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository,
    private val userPreferenceDataSource: UserPreferenceDataSourceImpl,
    private val userRepository: UserRepositoryImpl
) : ViewModel() {

    private val _responseLiveData = MutableLiveData<ResultWrapper<List<ItemResponse>>>()
    val responseLiveData: LiveData<ResultWrapper<List<ItemResponse>>>
        get() = _responseLiveData

    private val _categories = MutableLiveData<ResultWrapper<List<Category>>>()
    val categories : LiveData<ResultWrapper<List<Category>>>
        get() = _categories

    fun getMenus(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMenus(if(category == "all") null else category).collect() { result ->
                _responseLiveData.postValue(result)
            }
        }
    }


    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO){
            repository.getProducts().collect{

            }
        }
    }
}