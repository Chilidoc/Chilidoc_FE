package com.capstone.chilidoc.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.chilidoc.data.response.DataItem
import com.capstone.chilidoc.databinding.ItemRowArticleBinding
import com.capstone.chilidoc.ui.home.DetailArticleActivity

class ArticleAdapter : ListAdapter<DataItem, ArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailArticleActivity::class.java)
            intent.putExtra("id", article.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ItemRowArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: DataItem) {
            binding.tvArticleTitle.text = article.title
            binding.tvArticleDescription.text = article.content
            Glide.with(binding.root.context)
                .load(article.imageUrl)
                .into(binding.ivArticleImage)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}