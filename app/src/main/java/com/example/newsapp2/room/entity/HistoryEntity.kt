package com.example.newsapp2.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val newsId: String,
    val title: String,
    val source: String,
    val time: String,
    val imageUrl: String,
    val description: String,
    val url: String,
    val viewedAt: Long = System.currentTimeMillis()
)
