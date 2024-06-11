package com.capstone.chilidoc.data.repository

import com.capstone.chilidoc.data.api.ApiService
import com.capstone.chilidoc.data.pref.Preference
import com.capstone.chilidoc.data.pref.UserModel
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val apiService: ApiService,
    private val preference: Preference
) {
    suspend fun saveSession(user: UserModel) {
        preference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return preference.getSession()
    }

    suspend fun logout() {
        preference.logout()
    }

    companion object {
        fun getInstance(apiService: ApiService, userPreference: Preference) = Repository(apiService, userPreference)
    }
}