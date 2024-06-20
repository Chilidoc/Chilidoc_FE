package com.capstone.chilidoc.ui.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilidoc.databinding.ActivityResultBinding
import com.capstone.chilidoc.helper.reduceFileImage
import com.capstone.chilidoc.helper.uriToFile
import com.capstone.chilidoc.ui.ViewModelFactory
import com.capstone.chilidoc.ui.history.DetailHistoryActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel by viewModels<ScanViewModel> {
        ViewModelFactory.getInstance(this)
    }

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

        binding.btnSaveResult.setOnClickListener {
            val imageFile = uriToFile(image, this).reduceFileImage()
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )
            viewModel.saveResult(multipartBody)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.error.observe(this) {
            showToast(it)
        }
        viewModel.saveResponse.observe(this) {
            if (!it.success) {
                showToast("Gagal melanjutkan")
            } else {
                showToast("Proses berhasil")

                val intent = Intent(this, DetailHistoryActivity::class.java)
                intent.putExtra("id", it.data.id)
                startActivity(intent)
                finish()
            }
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