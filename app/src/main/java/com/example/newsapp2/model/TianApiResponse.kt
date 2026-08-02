package com.example.newsapp2.model

import com.google.gson.annotations.SerializedName

data class TianApiResponse(
    val code: Int,
    val msg: String,
    val result: NewsResult?
)

data class NewsResult(
    val curpage: Int,
    val allnum: Int,
    val newslist: List<TianNewsItem>?
)

data class TianNewsItem(
    val id: String?,
    val title: String,
    val description: String?,
    val source: String?,
    val ctime: String?,
    @SerializedName("picUrl") val picUrl: String?,
    val url: String?
)
