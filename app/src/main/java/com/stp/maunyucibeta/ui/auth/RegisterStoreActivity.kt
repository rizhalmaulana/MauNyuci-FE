package com.stp.maunyucibeta.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.databinding.ActivityRegisterStoreBinding


class RegisterStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftarLaundry.setOnClickListener {
            moveActivity(Intent(this@RegisterStoreActivity, RegisterAccActivity::class.java), R.anim.enter_from_right, R.anim.exit_to_left)
        }

        binding.ivBackStore.setOnClickListener {
            onBackPressed()
        }
    }

    private fun moveActivity(intent: Intent, transitionIn: Int, transitionOut: Int) {
        startActivity(intent)
        overridePendingTransition(transitionIn, transitionOut)
        finish()
    }

    private fun backActivity(intent: Intent, transitionIn: Int, transitionOut: Int) {
        startActivity(intent)
        overridePendingTransition(transitionIn, transitionOut)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backActivity(Intent(this@RegisterStoreActivity, LoginActivity::class.java), R.anim.exit_to_right, R.anim.enter_from_left)
    }
}