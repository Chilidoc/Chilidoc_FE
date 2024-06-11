package com.capstone.chilidoc.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.chilidoc.databinding.ActivityWelcomeBinding
import com.capstone.chilidoc.ui.ViewModelFactory
import com.capstone.chilidoc.ui.login.LoginActivity
import com.capstone.chilidoc.ui.register.RegisterActivity
import com.capstone.chilidoc.ui.splash.SplashViewModel

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val viewModel by viewModels<SplashViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                finish()
            }
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}