package com.capstone.chilidoc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.chilidoc.data.response.HistoryDataItem
import com.capstone.chilidoc.databinding.ItemRowHistoryBinding

class HistoryAdapter: ListAdapter<HistoryDataItem, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)

//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, DetailArticleActivity::class.java)
//            intent.putExtra("id", article.id)
//            holder.itemView.context.startActivity(intent)
//        }
    }

    class MyViewHolder(private val binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryDataItem) {
            binding.tvHistoryTitle.text = history.disease
            Glide.with(binding.root)
                .load(history.imageUrl)
                .into(binding.ivHistoryImage)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryDataItem>() {
            override fun areItemsTheSame(oldItem: HistoryDataItem, newItem: HistoryDataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HistoryDataItem, newItem: HistoryDataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}