package com.example.newsapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.repository.NewsRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _results = MutableLiveData<List<NewsItem>>(emptyList())
    val results: LiveData<List<NewsItem>> = _results

    private val _hint = MutableLiveData("输入关键词后按键盘搜索")
    val hint: LiveData<String> = _hint

    fun search(keyword: String) {
        val query = keyword.trim()
        if (query.isEmpty()) {
            _results.value = emptyList()
            _hint.value = "输入关键词后按键盘搜索"
            return
        }

        viewModelScope.launch {
            _hint.value = "搜索中..."
            _results.value = emptyList()
            val news = repository.searchNews(query)
            _results.value = news
            _hint.value = if (news.isEmpty()) "没有找到相关新闻" else ""
        }
    }
}
