package com.example.newsapp2.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String,
    val title: String,
    val source: String,
    val time: String,
    val imageUrl: String,
    val description: String,
    val url: String,
    val savedAt: Long = System.currentTimeMillis()
)