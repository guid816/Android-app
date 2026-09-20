package com.example.newsapp2.repository

import com.example.newsapp2.model.Category
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.model.TianNewsItem
import com.example.newsapp2.network.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope



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

    suspend fun searchNews(keyword: String): List<NewsItem> {
        val query = keyword.trim()
        if (query.isEmpty()) return emptyList()

        val all = coroutineScope {
            getCategories().map { category ->
                async {
                    try {
                        getNewsList(category.id)
                    } catch (_: Exception) {
                        emptyList()
                    }
                }
            }.awaitAll().flatten()
        }

        return all.distinctBy { it.id }.filter { news ->
            news.title.contains(query, ignoreCase = true) ||
                news.description.contains(query, ignoreCase = true)
        }
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