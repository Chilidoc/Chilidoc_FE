package com.capstone.chilidoc.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.chilidoc.data.pref.UserModel
import com.capstone.chilidoc.data.repository.Repository

class SplashViewModel(private val repository: Repository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}