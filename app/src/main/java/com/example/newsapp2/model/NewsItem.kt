package com.example.newsapp2.model


data class NewsItem(
    val id: String,
    val title: String,
    val source: String,
    val time: String,
    val imageUrl: String,
    val category: String,
    val description: String = "",
    val url: String = ""
)
