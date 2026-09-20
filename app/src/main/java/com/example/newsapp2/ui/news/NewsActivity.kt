package com.example.newsapp2.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp2.R
import com.example.newsapp2.databinding.ActivityNewsBinding
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.viewmodel.NewsDetailViewModel

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val news = NewsItem(
            id = intent.getStringExtra("id") ?: "",
            title = intent.getStringExtra("title") ?: "",
            source = intent.getStringExtra("source") ?: "",
            time = intent.getStringExtra("time") ?: "",
            imageUrl = intent.getStringExtra("imageUrl") ?: "",
            category = "",
            description = intent.getStringExtra("description") ?: "",
            url = intent.getStringExtra("url") ?: ""
        )

        binding.tvToolbarTitle.text = "新闻详情"
        binding.tvTitle.text = news.title
        binding.tvSource.text = news.source
        binding.tvTime.text = news.time
        binding.tvDescription.text = news.description

        viewModel.isBookmarked.observe(this) { bookmarked ->
            binding.ivFavorite.setImageResource(
                if (bookmarked) R.drawable.ic_star_filled
                else R.drawable.ic_star_empty
            )
        }

        viewModel.load(news)

        binding.ivFavorite.setOnClickListener {
            viewModel.toggleBookmark(news)
        }

        binding.tvViewOriginal.setOnClickListener {
            if (news.url.isNotEmpty()) {
                val url = if (news.url.startsWith("//")) "https:${news.url}" else news.url
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }

        binding.ivBack.setOnClickListener { finish() }

        binding.ivShare.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("还没做欸૮₍ ˊᯅˋ₎ა")
                .setPositiveButton("确定", null)
                .show()
        }
    }
}
