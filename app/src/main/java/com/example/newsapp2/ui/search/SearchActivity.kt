package com.example.newsapp2.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp2.databinding.ActivitySearchBinding
import com.example.newsapp2.ui.adapter.NewsAdapter
import com.example.newsapp2.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        adapter = NewsAdapter(emptyList()) { _, _ -> }
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        binding.rvResults.adapter = adapter

        binding.etKeyword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                viewModel.search(binding.etKeyword.text.toString())
                true
            } else {
                false
            }
        }

        viewModel.results.observe(this) { list ->
            adapter.submitList(list)
        }

        viewModel.hint.observe(this) { text ->
            binding.tvHint.text = text
            binding.tvHint.visibility = if (text.isEmpty()) View.GONE else View.VISIBLE
        }

        binding.etKeyword.requestFocus()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etKeyword.windowToken, 0)
    }
}
