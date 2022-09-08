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
            moveActivity(Intent(this@RegisterAccActivity, MainActivity::class.java), R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        }

        bindingRegister.ivBackAcc.setOnClickListener {
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
        backActivity(Intent(this@RegisterAccActivity, RegisterStoreActivity::class.java), R.anim.exit_to_right, R.anim.enter_from_left)
    }
}