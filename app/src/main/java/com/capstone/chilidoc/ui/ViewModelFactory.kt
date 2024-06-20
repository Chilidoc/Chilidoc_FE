package com.capstone.chilidoc.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.chilidoc.data.repository.Repository
import com.capstone.chilidoc.di.Injection
import com.capstone.chilidoc.ui.account.AccountViewModel
import com.capstone.chilidoc.ui.history.HistoryViewModel
import com.capstone.chilidoc.ui.home.DetailArticleViewModel
import com.capstone.chilidoc.ui.home.HomeViewModel
import com.capstone.chilidoc.ui.login.LoginViewModel
import com.capstone.chilidoc.ui.register.RegisterViewModel
import com.capstone.chilidoc.ui.scan.ScanViewModel
import com.capstone.chilidoc.ui.splash.SplashViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailArticleViewModel::class.java) -> {
                DetailArticleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) = ViewModelFactory(Injection.provideRepository(context))
    }
}