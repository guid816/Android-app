package com.example.newsapp2.ui.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp2.databinding.ActivityNewsBinding
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.newsapp2.R
import com.example.newsapp2.room.entity.BookmarkEntity
import com.example.newsapp2.room.entity.HistoryEntity
import kotlinx.coroutines.launch
import com.example.newsapp2.room.AppDatabase


class NewsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    private val db by lazy { AppDatabase.getInstance(this) }
    private val bookmarkDao by lazy { db.bookmarkDao() }
    private val historyDao by lazy { db.historyDao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")?: ""
        val source = intent.getStringExtra("source")?: ""
        val time = intent.getStringExtra("time")?: ""
        val description = intent.getStringExtra("description")?: ""
        val url = intent.getStringExtra("url")?: ""
        val newsId = intent.getStringExtra("id") ?: ""

        binding.tvToolbarTitle.text = "新闻详情"
        binding.tvTitle.text = title
        binding.tvSource.text = source
        binding.tvTime.text = time
        binding.tvDescription.text = description

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(
                newsId = newsId,
                title = title,
                source = source,
                time = time,
                imageUrl = "",
                description = description,
                url = url
            ))
        }

        lifecycleScope.launch {
            val bookmarked = bookmarkDao.isBookmarked(newsId)
            binding.ivFavorite.setImageResource(
                if (bookmarked) R.drawable.ic_star_filled
                else R.drawable.ic_star_empty
            )
            var isFav = bookmarked

            binding.ivFavorite.setOnClickListener {
                isFav = !isFav
                lifecycleScope.launch {
                    if (isFav) {
                        bookmarkDao.insert(BookmarkEntity(
                            id = newsId,
                            title = title,
                            source = source,
                            time = time,
                            imageUrl = "",
                            description = description,
                            url = url
                        ))
                    } else {
                        bookmarkDao.delete(BookmarkEntity(
                            id = newsId,
                            title = title,
                            source = source,
                            time = time,
                            imageUrl = "",
                            description = description,
                            url = url
                        ))
                    }
                }
                binding.ivFavorite.setImageResource(
                    if (isFav) R.drawable.ic_star_filled
                    else R.drawable.ic_star_empty
                )
            }
        }

        binding.tvViewOriginal.setOnClickListener {
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(
                    if (url.startsWith("//")) "https:$url" else url
                ))
                startActivity(intent)
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivShare.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("还没做欸૮₍ ˊᯅˋ₎ა")
                .setPositiveButton("确定", null)
                .show()
        }

    }
}