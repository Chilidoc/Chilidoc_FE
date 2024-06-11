package com.capstone.chilidoc.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: RegisterData,
)

data class RegisterData(

	@field:SerializedName("user")
	val user: RegisterUser,

	@field:SerializedName("token")
	val token: String,
)

data class RegisterUser(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("picture_url")
	val pictureUrl: Any,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,
)