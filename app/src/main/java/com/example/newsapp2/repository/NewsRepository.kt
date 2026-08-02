package com.example.newsapp2.repository

import com.example.newsapp2.model.Category
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.model.TianNewsItem
import com.example.newsapp2.network.RetrofitClient



class NewsRepository {
    private val api = RetrofitClient.apiService

    private val apiKey = "cd399396f969eba18b64d8e37f413f67"

    fun getCategories(): List<Category> = listOf(
        
        Category("generalnews", "推荐"),
        Category("it", "IT咨询"),
        Category("dongman", "动漫资讯"),
        Category("film", "影视资讯"),
        Category("internet", "互联网资讯"),
        Category("sicprobe", "科学探索"),
        Category("huanbao", "环保资讯"),
        Category("esports", "电竞资讯"),
        
    )

    suspend fun getNewsList(category: String): List<NewsItem> {
        val response = api.getHeadlines(
            apiKey = apiKey,
            category = category
        )
        return response.result?.newslist?.map { it.toNewsItem() } ?: emptyList()
    }

    private fun TianNewsItem.toNewsItem() = NewsItem(
        id = url.hashCode().toString(),
        title = title,
        source = source ?: "未知来源",
        time = ctime ?: "",
        imageUrl = picUrl ?: "",
        category = "",
        description = description ?: "",   
        url = url ?: "" 
    )
}