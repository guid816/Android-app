package com.example.newsapp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.repository.LocalNewsRepository
import com.example.newsapp2.room.AppDatabase

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LocalNewsRepository(AppDatabase.getInstance(application))

    val bookmarks: LiveData<List<NewsItem>> = repository.getBookmarks().asLiveData()
}
