package com.capstone.chilidoc.data.api

import com.capstone.chilidoc.data.pref.LoginRequest
import com.capstone.chilidoc.data.pref.RegisterRequest
import com.capstone.chilidoc.data.response.ArticleResponse
import com.capstone.chilidoc.data.response.DetailArticleResponse
import com.capstone.chilidoc.data.response.LoginResponse
import com.capstone.chilidoc.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("stories/detail/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: String,
    ): DetailArticleResponse
}