package com.example.newsapp2.repository

import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.room.AppDatabase
import com.example.newsapp2.room.entity.BookmarkEntity
import com.example.newsapp2.room.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalNewsRepository(private val db: AppDatabase) {

    fun getBookmarks(): Flow<List<NewsItem>> =
        db.bookmarkDao().getAll().map { list -> list.map { it.toNewsItem() } }

    fun getHistory(): Flow<List<NewsItem>> =
        db.historyDao().getAll().map { list -> list.map { it.toNewsItem() } }

    suspend fun isBookmarked(newsId: String): Boolean =
        db.bookmarkDao().isBookmarked(newsId)

    suspend fun addHistory(item: NewsItem) {
        db.historyDao().insert(item.toHistoryEntity())
    }

    suspend fun toggleBookmark(item: NewsItem): Boolean {
        return if (db.bookmarkDao().isBookmarked(item.id)) {
            db.bookmarkDao().delete(item.toBookmarkEntity())
            false
        } else {
            db.bookmarkDao().insert(item.toBookmarkEntity())
            true
        }
    }

    private fun BookmarkEntity.toNewsItem() = NewsItem(
        id = id,
        title = title,
        source = source,
        time = time,
        imageUrl = imageUrl,
        category = "",
        description = description,
        url = url
    )

    private fun HistoryEntity.toNewsItem() = NewsItem(
        id = newsId,
        title = title,
        source = source,
        time = time,
        imageUrl = imageUrl,
        category = "",
        description = description,
        url = url
    )

    private fun NewsItem.toBookmarkEntity() = BookmarkEntity(
        id = id,
        title = title,
        source = source,
        time = time,
        imageUrl = imageUrl,
        description = description,
        url = url
    )

    private fun NewsItem.toHistoryEntity() = HistoryEntity(
        newsId = id,
        title = title,
        source = source,
        time = time,
        imageUrl = imageUrl,
        description = description,
        url = url
    )
}
