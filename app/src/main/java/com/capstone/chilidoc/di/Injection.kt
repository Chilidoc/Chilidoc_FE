package com.capstone.chilidoc.di

import android.content.Context
import com.capstone.chilidoc.data.api.ApiConfig
import com.capstone.chilidoc.data.pref.Preference
import com.capstone.chilidoc.data.pref.dataStore
import com.capstone.chilidoc.data.repository.Repository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = Preference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return Repository.getInstance(apiService, pref)
    }
}