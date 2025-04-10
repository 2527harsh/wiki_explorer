package com.example.wikiexplorer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wikiexplorer.R
import com.example.wikiexplorer.data.db.CategoryEntity

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val categories = mutableListOf<CategoryEntity>()

    fun submitList(newItems: List<CategoryEntity>) {
        val previousSize = categories.size
        categories.addAll(newItems)
        notifyItemRangeInserted(previousSize, newItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categories[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val categoryTitle: TextView = view.findViewById(R.id.categoryTitle)
        fun bind(category: CategoryEntity) {
            categoryTitle.text = category.category
        }
    }
}
