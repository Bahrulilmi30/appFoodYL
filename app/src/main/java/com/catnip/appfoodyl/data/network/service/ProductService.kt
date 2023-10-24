package com.catnip.appfoodyl.data.network.service

import com.catnip.appfoodyl.BuildConfig
import com.catnip.appfoodyl.data.network.api.model.category.CategoryResponse
import com.catnip.appfoodyl.data.network.api.model.menu.MenuResponse
import com.catnip.appfoodyl.data.network.firebase.data.ProductResponse
import com.catnip.appfoodyl.data.network.api.model.order.OrderRequest
import com.catnip.appfoodyl.data.network.api.model.order.OrderResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ProductService {
    @GET("listmenu")
    suspend fun getProduct(category: String?): MenuResponse

    @GET("category")
    suspend fun getCategories() : CategoryResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrderResponse

    companion object {
        @JvmStatic
        operator fun invoke(): ProductService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ProductService::class.java)
        }
    }
}