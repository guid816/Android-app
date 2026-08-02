package com.example.newsapp2.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp2.room.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: BookmarkEntity)

    @Delete
    suspend fun delete(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAll(): Flow<List<BookmarkEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM bookmarks WHERE id = :newsId)")
    suspend fun isBookmarked(newsId: String): Boolean
}