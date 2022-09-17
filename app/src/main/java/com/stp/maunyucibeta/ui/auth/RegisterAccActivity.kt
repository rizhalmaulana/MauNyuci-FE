package com.stp.maunyucibeta.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stp.maunyucibeta.MainActivity
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.databinding.ActivityRegisterAccBinding

class RegisterAccActivity : AppCompatActivity() {

    private lateinit var bindingRegister: ActivityRegisterAccBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingRegister = ActivityRegisterAccBinding.inflate(layoutInflater)
        setContentView(bindingRegister.root)

        bindingRegister.btnDaftarAkun.setOnClickListener {
            moveActivity(Intent(this@RegisterAccActivity, MainActivity::class.java))
        }

        bindingRegister.ivBackAcc.setOnClickListener {
            backActivity(Intent(this@RegisterAccActivity, RegisterStoreActivity::class.java))
        }
    }

    private fun moveActivity(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun backActivity(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.exit_to_right, R.anim.enter_from_left)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backActivity(Intent(this@RegisterAccActivity, RegisterStoreActivity::class.java), )
    }
}