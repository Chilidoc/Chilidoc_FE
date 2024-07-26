package com.capstone.chilidoc.ui.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilidoc.data.pref.RegisterRequest
import com.capstone.chilidoc.databinding.ActivityRegisterBinding
import com.capstone.chilidoc.ui.ViewModelFactory
import com.capstone.chilidoc.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loginPage()

        nameValidation()
        emailValidation()
        passwordValidation()

        binding.btnCreateAccount.setOnClickListener {
            val isNameValid = binding.nameTextLayout.error
            val isEmailValid = binding.emailTextLayout.error
            val isPasswordValid = binding.passwordTextLayout.error

            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("Lengkapi data terlebih dahulu")
            } else if (isNameValid != null || isEmailValid != null || isPasswordValid != null) {
                showToast("Perbaiki data terlebih dahulu")
            } else if (password != confirmPassword) {
                showToast("Password tidak sama")
            } else {
                val registerRequest = RegisterRequest(name, email, password, confirmPassword)
                viewModel.registerUser(registerRequest)
            }
        }

        viewModel.isLoading.observe(this)
        {
            showLoading(it)
        }
        viewModel.error.observe(this)
        {
            showToast(it)
        }
        viewModel.registerResponse.observe(this)
        {
            if (it.success) {
                showToast(it.message)
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, 2000)
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loginPage() {
        val text = "Sudah punya akun? Masuk"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#0000FF"))

        val startIndex = text.indexOf("Masuk")
        val endIndex = startIndex + "Masuk".length

        spannableString.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            foregroundColorSpan,
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvHaveAccount.text = spannableString
        binding.tvHaveAccount.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun nameValidation() {
        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val name = s.toString()

                binding.nameTextLayout.error = when {
                    name.isEmpty() -> {
                        "Nama tidak boleh kosong"
                    }

                    name.length < 5 -> {
                        "Nama minimal 5 karakter"
                    }

                    else -> {
                        ""
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun emailValidation() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()

                binding.emailTextLayout.error = when {
                    email.isEmpty() -> {
                        "Email tidak boleh kosong"
                    }

                    !email.contains("@") || !email.contains(".") -> {
                        "Email tidak valid"
                    }

                    else -> {
                        ""
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun passwordValidation() {
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pass = s.toString()

                binding.passwordTextLayout.error = when {
                    pass.isEmpty() -> {
                        "Password tidak boleh kosong"
                    }

                    pass.length < 6 -> {
                        "Password minimal 6 karakter"
                    }

                    else -> {
                        ""
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}