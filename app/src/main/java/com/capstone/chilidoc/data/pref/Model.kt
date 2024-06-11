package com.capstone.chilidoc.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean,
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)