package com.capstone.chilidoc.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.chilidoc.databinding.ActivityDetailArticleBinding
import com.capstone.chilidoc.ui.ViewModelFactory

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding
    private val viewModel by viewModels<DetailArticleViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Artikel"
            setDisplayHomeAsUpEnabled(true)
        }

        val id = intent.getIntExtra("id", 0)
        viewModel.getDetailArticle(id)

        viewModel.article.observe(this) {
            binding.apply {
                tvArticleTitle.text = it.title
                tvArticleDescription.text = it.content
                Glide.with(root)
                    .load(it.imageUrl)
                    .into(ivArticleImage)
            }
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.error.observe(this) {
            showToast(it)
        }
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}