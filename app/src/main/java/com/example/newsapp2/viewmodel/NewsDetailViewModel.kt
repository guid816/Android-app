package com.example.newsapp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.repository.LocalNewsRepository
import com.example.newsapp2.room.AppDatabase
import kotlinx.coroutines.launch

class NewsDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LocalNewsRepository(AppDatabase.getInstance(application))

    private val _isBookmarked = MutableLiveData<Boolean>()
    val isBookmarked: LiveData<Boolean> = _isBookmarked

    fun load(item: NewsItem) {
        viewModelScope.launch {
            repository.addHistory(item)
            _isBookmarked.value = repository.isBookmarked(item.id)
        }
    }

    fun toggleBookmark(item: NewsItem) {
        viewModelScope.launch {
            _isBookmarked.value = repository.toggleBookmark(item)
        }
    }
}
