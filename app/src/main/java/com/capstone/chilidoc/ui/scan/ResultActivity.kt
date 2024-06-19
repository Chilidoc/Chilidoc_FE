package com.capstone.chilidoc.ui.scan

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilidoc.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Scan Result"
            setDisplayHomeAsUpEnabled(true)
        }

        val image = Uri.parse(intent.getStringExtra("image"))
        image.let {
            binding.ivAnalyze.setImageURI(it)
        }

        val result = intent.getStringArrayExtra("result")
        result?.let {
            binding.tvAnalyzePrediction.text = "Prediksi :\n${it[0]}"
            binding.tvAnalyzeConfidence.text = "Hasil :\n${it[1]}"
        }
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}