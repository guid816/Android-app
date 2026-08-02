package com.example.newsapp2.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp2.databinding.FragmentHomeBinding
import com.example.newsapp2.ui.search.SearchActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp2.ui.adapter.CategoryAdapter
import com.example.newsapp2.ui.adapter.NewsAdapter
import com.example.newsapp2.viewmodel.HomeViewModel

class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.categories.observe(viewLifecycleOwner) {
            categories ->
                categoryAdapter = CategoryAdapter(categories) {
                    _, category ->
                        viewModel.loadNews(category.id)
                }
                binding.rvCategories.adapter = categoryAdapter
        }

        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())

        newsAdapter = NewsAdapter(emptyList()) { position, news ->
            // 点击新闻的逻辑
        }
        binding.rvNews.adapter = newsAdapter

        viewModel.newsList.observe(viewLifecycleOwner) { list ->
            newsAdapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}