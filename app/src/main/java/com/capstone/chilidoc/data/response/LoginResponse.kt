package com.capstone.chilidoc.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: LoginData,
)

data class LoginData(

	@field:SerializedName("user")
	val user: LoginUser,

	@field:SerializedName("token")
	val token: String
)

data class LoginUser(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("address")
	val address: Any,

	@field:SerializedName("gender")
	val gender: Any,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("picture_url")
	val pictureUrl: Any,

	@field:SerializedName("phone")
	val phone: Any,

	@field:SerializedName("birth_date")
	val birthDate: Any,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any,

	@field:SerializedName("picture")
	val picture: Any
)
