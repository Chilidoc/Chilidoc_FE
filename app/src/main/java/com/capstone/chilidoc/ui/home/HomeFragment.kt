package com.capstone.chilidoc.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.chilidoc.databinding.FragmentHomeBinding
import com.capstone.chilidoc.ui.ViewModelFactory
import com.capstone.chilidoc.ui.adapter.ArticleAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = ArticleAdapter()
        binding.rvArticle.layoutManager = LinearLayoutManager(context)
        binding.rvArticle.adapter = adapter

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            val name = user.name.replaceFirstChar { it.uppercase() }
            binding.tvWelcome.text = "Selamat datang, $name \uD83D\uDC4B\uD83C\uDFFB"
        }

        viewModel.getArticles()
        viewModel.articleResponse.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showToast(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
