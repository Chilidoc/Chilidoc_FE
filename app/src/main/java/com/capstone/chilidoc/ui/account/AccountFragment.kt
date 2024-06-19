package com.capstone.chilidoc.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.chilidoc.databinding.FragmentAccountBinding
import com.capstone.chilidoc.ui.ViewModelFactory

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AccountViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.tvNameUser.text = user.name.replaceFirstChar { it.uppercase() }
            binding.tvEmailUser.text = user.email
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Konfirmasi!")
                setMessage("Yakin ingin keluar?")
                setPositiveButton("Ya") { _, _ ->
                    viewModel.logout()
                    showToast("Berhasil keluar")
                }
                setNegativeButton("Batal") { _, _ -> }
                create()
                show()
            }
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
}