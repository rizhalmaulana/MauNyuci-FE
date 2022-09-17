package com.stp.maunyucibeta.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stp.maunyucibeta.MainActivity
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var bindingLogin: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        bindingLogin.tvDaftarDisini.setOnClickListener {
            moveActivity(Intent(this@LoginActivity, RegisterStoreActivity::class.java), R.anim.anim_slide_in_left, R.anim.anim_slide_out_left)
        }

        bindingLogin.btnLogin.setOnClickListener {
            moveActivity(Intent(this@LoginActivity, MainActivity::class.java), R.anim.fade_in, R.anim.fade_out)
        }
    }

    private fun moveActivity(intent: Intent, transitionIn: Int, transitionOut: Int) {
        startActivity(intent)
        overridePendingTransition(transitionIn, transitionOut)
        finish()
    }
}