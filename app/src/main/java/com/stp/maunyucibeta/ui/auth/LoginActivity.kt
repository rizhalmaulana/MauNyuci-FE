package com.stp.maunyucibeta.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.stp.maunyucibeta.MainActivity
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.base.BaseActivity
import com.stp.maunyucibeta.databinding.ActivityLoginBinding
import com.stp.maunyucibeta.extension.click
import com.stp.maunyucibeta.extension.color
import com.stp.maunyucibeta.extension.underline
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_login
    private val viewModel by viewModels<AuthViewModel>()

    override fun ActivityLoginBinding.initializeView(savedInstanceState: Bundle?) {
        setupdoLogin()
        setupObserve()
    }

    @SuppressLint("LogNotTimber")
    private fun ActivityLoginBinding.setupObserve() {
        viewModel.apply {
            binding.apply {
                resetState()

                loading.observe {
                    this?.let {
                        etEmailLogin.isEnabled = !it
                        etPassLogin.isEnabled = !it
                        if (it) {
                            Log.i("Login", "observe loading: $it")

                            lrProgressbar.visibility = VISIBLE
                        } else {
                            Log.i("Login", "observe loading: $it")

                            lrProgressbar.visibility = GONE
                        }
                    }
                }

                isValid.observe {
                    Log.i("Login", "observe isValid: $this")
                    this.let {
                        if (it) {
                            btnLogin.isEnabled = it
                            btnLogin.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.button_gradient))
                        } else {
                            btnLogin.isEnabled = it
                            btnLogin.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.button_gradient_invalid))
                        }
                    }
                }

                hideErrorEmailFormat.observe {
                    when {
                        this -> txtEmailValidation.visibility = GONE
                        etEmailLogin.text?.isEmpty() == true -> txtEmailValidation.visibility =
                            GONE
                        else -> txtEmailValidation.visibility = VISIBLE
                    }
                }

                hideErrorPasswordFormat.observe {
                    when {
                        this -> txtPasswordValidation.visibility = GONE
                        etPassLogin.text?.isEmpty() == true -> txtPasswordValidation.visibility =
                            GONE
                        else -> txtPasswordValidation.visibility = VISIBLE
                    }
                }

                message.observe {
                    this.getContentIfNotHandled()?.let {
                        Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
                    }
                }

                error.observe {
                    if (this != null) {
                        this@LoginActivity.showError(
                            this,
                            message ?: errorMessage,
                            ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.ic_caution
                            )
                        ) {}
                    }
                }

                loginError.observe {
                    if (this != null) {
                        this@LoginActivity.showError(this, this.toString(),
                            ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.ic_caution
                            )
                        ) {}
                    }
                }

                login.observe {
                    if (this != null) {
                        Toast.makeText(
                            applicationContext,
                            "Selamat Datang " + (this.name),
                            Toast.LENGTH_LONG
                        ).show()
                        moveActivity()
                    }
                }
            }
        }
    }

    private val errorMessage: SpannableStringBuilder by lazy {
        SpannableStringBuilder()
            .append(getString(R.string.label_blocking_description))
            .append(
                getString(R.string.label_here).color(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorPrimary
                    )
                ).underline().click { }
            )
    }

    private fun ActivityLoginBinding.setupdoLogin() {
        viewModel.attachInputFormat(etEmailLogin, etPassLogin)

        var passwordHidden = false
        ivPassToggle.setOnClickListener {
            passwordHidden = if (passwordHidden) {
                passwordToggleVisibility(ivPassToggle, etPassLogin, false)
            } else {
                passwordToggleVisibility(ivPassToggle, etPassLogin, true)
            }
        }

        btnLogin.setOnClickListener {
            val map: HashMap<String, String> = hashMapOf()
            map["input"] = etEmailLogin.text.toString()
            map["password"] = etPassLogin.text.toString()
            viewModel.fetchLogin(map)
        }
    }

    private fun moveActivity() {
        val intent = Intent(
            this,
            MainActivity::class.java
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}