package com.capstone.chilidoc.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.chilidoc.databinding.ActivityDetailHistoryBinding
import com.capstone.chilidoc.ui.ViewModelFactory

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding
    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Scan Detail"
            setDisplayHomeAsUpEnabled(true)
        }

        val id = intent.getIntExtra("id", 0)
        viewModel.getDetailHistory(id)

        viewModel.detailHistory.observe(this) {
            binding.apply {
                tvDisease.text = it.disease
                tvResult.text = it.result
                tvPrevent.text = it.prevention
                tvTreatment.text = it.treatment
                Glide.with(root)
                    .load(it.imageUrl)
                    .into(ivAnalyze)
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