package com.stp.maunyucibeta

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.stp.maunyucibeta.databinding.ActivitySplashScreenBinding
import com.stp.maunyucibeta.ui.auth.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var bindingSplash: ActivitySplashScreenBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityScope.launch {
            delay(4000)

            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        bindingSplash = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(bindingSplash.root)

        bindingSplash.imgAnimation
        Glide.with(applicationContext)
            .load(R.raw.laundry)
            .into(bindingSplash.imgAnimation)
    }

    companion object {
        private const val TAG = "SplashScreenActivity"
    }
}