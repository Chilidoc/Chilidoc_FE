package com.capstone.chilidoc.data.response

import com.google.gson.annotations.SerializedName

data class DetailHistoryResponse(

	@field:SerializedName("data")
	val data: HistoryDetailData,

	@field:SerializedName("success")
	val success: Boolean
)

data class HistoryDetailData(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("result")
	val result: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("treatment")
	val treatment: String,

	@field:SerializedName("disease")
	val disease: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("prevention")
	val prevention: String,
)
