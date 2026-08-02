package com.example.newsapp2.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.newsapp2.databinding.ActivityBookmarkBinding
import com.example.newsapp2.room.AppDatabase
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.ui.adapter.NewsAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BookmarkActivity: AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkBinding
    private val db by lazy { AppDatabase.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.rvList.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val bookmarks = db.bookmarkDao().getAll().first()
            val items = bookmarks.map {
                b ->
                NewsItem(
                    b.id,
                    b.title,
                    b.source,
                    b.time,
                    b.imageUrl,
                    "",
                    b.description,
                    b.url
                )
            }
            val adapter = NewsAdapter(items) {
                _, news ->
                val intent = android.content.Intent(this@BookmarkActivity,
                    com.example.newsapp2.ui.news.NewsActivity::class.java).apply {
                        putExtra("id", news.id)
                        putExtra("title", news.title)
                        putExtra("source", news.source)
                        putExtra("time", news.time)
                        putExtra("description", news.description)
                        putExtra("url", news.url)
                        putExtra("imageUrl", news.imageUrl)
                    }
                startActivity(intent)
            }
            binding.rvList.adapter = adapter
        }
    }


}