package com.capstone.chilidoc.data.api

import com.capstone.chilidoc.data.pref.LoginRequest
import com.capstone.chilidoc.data.pref.RegisterRequest
import com.capstone.chilidoc.data.response.ArticleResponse
import com.capstone.chilidoc.data.response.DetailArticleResponse
import com.capstone.chilidoc.data.response.HistoryResponse
import com.capstone.chilidoc.data.response.LoginResponse
import com.capstone.chilidoc.data.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("auth/register")
    suspend fun registerUser(
        @Body request: RegisterRequest,
    ): RegisterResponse

    @POST("auth/login")
    suspend fun loginUser(
        @Body request: LoginRequest,
    ): LoginResponse

    @GET("article")
    suspend fun getArticles(): ArticleResponse

    @GET("article/detail/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: Int,
    ): DetailArticleResponse

    @GET("history")
    suspend fun getHistory(): HistoryResponse

    @POST("history/create")
    suspend fun saveHistory(
        @Part file: MultipartBody.Part,
    ): HistoryResponse
}