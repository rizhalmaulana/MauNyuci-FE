package com.stp.maunyucibeta

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.stp.maunyucibeta.base.BaseActivity
import com.stp.maunyucibeta.databinding.ActivitySplashScreenBinding
import com.stp.maunyucibeta.ui.auth.AuthViewModel
import com.stp.maunyucibeta.ui.auth.LoginActivity
import com.stp.maunyucibeta.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    private val authViewModel by viewModels<AuthViewModel>()
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun getLayoutId(): Int = R.layout.activity_splash_screen

    override fun ActivitySplashScreenBinding.initializeView(savedInstanceState: Bundle?) {
        activityScope.launch {
            delay(Constants.SPLASH_SCREEN_DELAY_DURATION)
            if (authViewModel.isAnonymous()) {
                goToNextDestination(
                    Intent(applicationContext, LoginActivity::class.java)
                )
            } else {
                goToNextDestination(
                    Intent(applicationContext, MainActivity::class.java)
                )
            }
        }

        Glide.with(applicationContext)
            .load(R.raw.laundry)
            .into(binding.imgAnimation)
    }

    private fun goToNextDestination(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}