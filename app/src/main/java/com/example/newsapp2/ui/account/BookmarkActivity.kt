package com.example.newsapp2.ui.account

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp2.databinding.ActivityBookmarkBinding
import com.example.newsapp2.ui.adapter.NewsAdapter
import com.example.newsapp2.viewmodel.BookmarkViewModel

class BookmarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkBinding
    private val viewModel: BookmarkViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = NewsAdapter(emptyList()) { _, _ -> }
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter

        viewModel.bookmarks.observe(this) { list ->
            adapter.submitList(list)
        }
    }
}
