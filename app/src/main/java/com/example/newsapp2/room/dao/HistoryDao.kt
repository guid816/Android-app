package com.example.newsapp2.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.newsapp2.room.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.Query


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: HistoryEntity)

    @Query("SELECT * FROM history ORDER BY viewedAt DESC LIMIT 100")
    fun getAll(): Flow<List<HistoryEntity>>

    @Query("DELETE FROM history")
    suspend fun clearAll()
}