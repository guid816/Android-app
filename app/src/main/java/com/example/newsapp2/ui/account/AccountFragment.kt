package com.example.newsapp2.ui.account

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp2.databinding.FragmentAccountBinding
import android.content.Intent


class AccountFragment: Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llAbout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("关于")
                .setMessage("关注塔菲喵，关注塔菲谢谢喵૮₍ ˊᯅˋ₎ა，觉得还行可以Star一下吗")
                .setPositiveButton("确定", null)
                .show()
        }

        binding.llBookmarks.setOnClickListener {
            val intent = Intent(requireContext(), BookmarkActivity::class.java)
            startActivity(intent)

        }

        binding.llHistory.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}