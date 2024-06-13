package com.capstone.chilidoc.data.repository

import com.capstone.chilidoc.data.api.ApiService
import com.capstone.chilidoc.data.pref.LoginRequest
import com.capstone.chilidoc.data.pref.Preference
import com.capstone.chilidoc.data.pref.RegisterRequest
import com.capstone.chilidoc.data.pref.UserModel
import com.capstone.chilidoc.data.response.ArticleResponse
import com.capstone.chilidoc.data.response.LoginResponse
import com.capstone.chilidoc.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val apiService: ApiService,
    private val preference: Preference,
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

    suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.loginUser(request)
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return apiService.registerUser(request)
    }

    suspend fun getArticles(): ArticleResponse {
        return apiService.getArticles()
    }

    companion object {
        fun getInstance(apiService: ApiService, userPreference: Preference) =
            Repository(apiService, userPreference)
    }
}