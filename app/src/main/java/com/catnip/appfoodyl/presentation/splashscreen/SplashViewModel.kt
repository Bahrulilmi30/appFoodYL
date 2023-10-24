package com.catnip.appfoodyl.presentation.splashscreen

import androidx.lifecycle.ViewModel
import com.catnip.appfoodyl.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repository.isLoggedIn()

}