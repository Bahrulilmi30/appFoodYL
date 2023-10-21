package com.catnip.appfoodyl.data.repository

import com.catnip.appfoodyl.data.model.User
import com.catnip.appfoodyl.data.model.toUser
import com.catnip.appfoodyl.data.network.firebase.auth.FirebaseAuthDataSource
import com.catnip.appfoodyl.utils.ResultWrapper
import com.catnip.appfoodyl.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun isLoggedIn():Boolean
    fun getCurrentUser(): User?
    fun doLogout(): Boolean
    suspend fun doRegister(fullName: String, email: String, password: String): Flow<ResultWrapper<Boolean>>
    suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>>
}

class UserRepositoryImpl(
    private val dataSource: FirebaseAuthDataSource
):UserRepository{
    override fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return dataSource.getCurrentUser().toUser()
    }

    override fun doLogout(): Boolean {
        return dataSource.doLogout()
    }

    override suspend fun doRegister(
        fullName: String,
        email: String,
        password: String
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow( dataSource.doRegister(fullName, email, password))
    }

    override suspend fun doLogin(email: String, password: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow( dataSource.doLogin(email, password))
    }

}