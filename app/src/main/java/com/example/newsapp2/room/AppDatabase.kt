package com.example.newsapp2.room

import androidx.room.Database
import com.example.newsapp2.room.entity.BookmarkEntity
import com.example.newsapp2.room.entity.HistoryEntity
import com.example.newsapp2.room.dao.BookmarkDao
import com.example.newsapp2.room.dao.HistoryDao
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    version = 1,
    entities = [
        BookmarkEntity::class,
        HistoryEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "newsapp2.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}