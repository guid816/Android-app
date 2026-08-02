package com.example.newsapp2.ui.adapter

import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import com.example.newsapp2.R
import com.example.newsapp2.databinding.ItemCategoryBinding
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.newsapp2.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClick: (Int, Category) -> Unit
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var selectedPosition = 0

    class ViewHolder(val binding: ItemCategoryBinding)
    : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]

        holder.binding.tvCategory.text = category.name

        if(position == selectedPosition){
            holder.binding.tvCategory.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.bg_category_selected
            )
            holder.binding.tvCategory.setTextColor(Color.WHITE)
        }else {
            holder.binding.tvCategory.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.bg_category_normal
            )
            holder.binding.tvCategory.setTextColor(Color.parseColor("#333333"))
        }

        holder.itemView.setOnClickListener {
            val realPosition = holder.adapterPosition
            if (realPosition == RecyclerView.NO_POSITION) return@setOnClickListener 
    
            selectedPosition = realPosition
            notifyDataSetChanged()
            onItemClick(realPosition, category)
        }
    }

    override fun getItemCount(): Int = categories.size
}