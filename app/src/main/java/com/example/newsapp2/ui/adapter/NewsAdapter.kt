package com.example.newsapp2.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp2.databinding.ItemNewsBinding
import com.example.newsapp2.model.NewsItem
import com.example.newsapp2.ui.news.NewsActivity

class NewsAdapter(
    private var newsList: List<NewsItem>,
    private val onItemClick: (Int, NewsItem) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    class ViewHolder(val binding: ItemNewsBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }    

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        
        val news = newsList[position]
        holder.binding.tvTitle.text = news.title
        holder.binding.tvSource.text = news.source
        holder.binding.tvTime.text = news.time
        
        val intent = Intent(holder.itemView.context,
            NewsActivity::class.java).apply {
            putExtra("id", news.id)    
            putExtra("title", news.title)
            putExtra("source", news.source)
            putExtra("time", news.time)
            putExtra("description", news.description)
            putExtra("url", news.url)
            putExtra("imageUrl", news.imageUrl)
        }

        val cover = holder.binding.ivCover
        if (news.imageUrl.isEmpty()) {
            Glide.with(cover).clear(cover)
            cover.visibility = View.GONE
        } else {
            cover.visibility = View.VISIBLE
            Glide.with(cover)
                .load(news.imageUrl)
                .centerCrop()
                .into(cover)
        }

        holder.itemView.setOnClickListener {
            val realPos = holder.adapterPosition
            if (realPos != RecyclerView.NO_POSITION) {
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = newsList.size

    fun submitList(list: List<NewsItem>) {
        newsList = list
        notifyDataSetChanged()
    }
}