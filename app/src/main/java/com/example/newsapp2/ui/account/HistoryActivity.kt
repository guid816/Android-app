package com.example.newsapp2.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.newsapp2.databinding.ActivityHistoryBinding
import com.example.newsapp2.room.AppDatabase
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp2.model.NewsItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.newsapp2.ui.adapter.NewsAdapter

class HistoryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val db by lazy { AppDatabase.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.rvList.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val history = db.historyDao().getAll().first()
            val items = history.map {
                h ->
                NewsItem(
                    h.newsId,
                    h.title,
                    h.source,
                    h.time,
                    h.imageUrl,
                    "",
                    h.description,
                    h.url
                )
            }

            val adapter = NewsAdapter(items) {
                _, news ->
                val intent = Intent(this@HistoryActivity,
                    com.example.newsapp2.ui.news.NewsActivity::class.java).apply {
                        putExtra("id", news.id)
                        putExtra("title", news.title)
                        putExtra("source", news.source)
                        putExtra("time", news.time)
                        putExtra("description", news.description)
                        putExtra("url", news.url)
                        putExtra("imageUrl", news.imageUrl)
                }
            }
            binding.rvList.adapter = adapter
            }
        }
    }