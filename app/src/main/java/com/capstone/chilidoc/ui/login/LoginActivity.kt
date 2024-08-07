package com.capstone.chilidoc.ui.login

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
import com.capstone.chilidoc.data.pref.LoginRequest
import com.capstone.chilidoc.data.pref.UserModel
import com.capstone.chilidoc.databinding.ActivityLoginBinding
import com.capstone.chilidoc.ui.MainActivity
import com.capstone.chilidoc.ui.ViewModelFactory
import com.capstone.chilidoc.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        registerPage()

        emailValidation()
        passwordValidation()

        binding.btnLogin.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val isEmail = binding.emailEditText.error
            val isPassword = binding.passwordEditText.error

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Lengkapi data terlebih dahulu")
            } else if (isEmail != null || isPassword != null) {
                showToast("Perbaiki data terlebih dahulu")
            } else {
                val loginRequest = LoginRequest(email, password)
                viewModel.loginUser(loginRequest)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.error.observe(this) {
            showToast(it)
        }
        viewModel.loginResponse.observe(this) {
            if (it.success) {
                val name = it.data.user.name
                val email = it.data.user.email
                val token = it.data.token
                viewModel.saveSession(UserModel(name, email, token))

                showToast(it.message)
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
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

    private fun registerPage() {
        val text = "Belum punya akun? Daftar"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#0000FF"))

        val startIndex = text.indexOf("Daftar")
        val endIndex = startIndex + "Daftar".length

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

        binding.tvNotHaveAccount.text = spannableString
        binding.tvNotHaveAccount.movementMethod = LinkMovementMethod.getInstance()
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