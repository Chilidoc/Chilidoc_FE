package com.capstone.chilidoc.data.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: List<DataItem>,
)

data class DataItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("updated_at")
    val updatedAt: Any,

    @field:SerializedName("image_url")
    val imageUrl: String,

    @field:SerializedName("created_at")
    val createdAt: Any,
)
