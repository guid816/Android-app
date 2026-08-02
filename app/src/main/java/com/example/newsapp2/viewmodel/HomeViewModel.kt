package com.example.newsapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp2.model.Category
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.repository.NewsRepository
import kotlinx.coroutines.launch


class HomeViewModel: ViewModel() {
    private val repository = NewsRepository()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _newsList = MutableLiveData<List<NewsItem>>()
    val newsList: LiveData<List<NewsItem>> = _newsList

    init {
        _categories.value = repository.getCategories()
        loadNews("generalnews")
    }

    fun loadNews(category: String) {
        viewModelScope.launch {
            val news = repository.getNewsList(category)
            _newsList.value = news
        }
    }

}