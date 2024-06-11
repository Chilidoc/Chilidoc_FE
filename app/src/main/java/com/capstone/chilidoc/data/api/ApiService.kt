package com.capstone.chilidoc.data.api

import com.capstone.chilidoc.data.pref.RegisterRequest
import com.capstone.chilidoc.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): RegisterResponse
}