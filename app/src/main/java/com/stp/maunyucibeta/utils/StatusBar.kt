package com.stp.maunyucibeta.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.stp.maunyucibeta.R

class StatusBar {
    companion object {
        fun setLightStatusBar(window: Window) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var flags: Int =
                    window.decorView.systemUiVisibility // get current flag
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // add LIGHT_STATUS_BAR to flag
                window.decorView.systemUiVisibility = flags
                window.statusBarColor = Color.WHITE
            }
        }

        fun setLoginStatusBar(window: Window) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var flags: Int =
                    window.decorView.systemUiVisibility // get current flag
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // add LIGHT_STATUS_BAR to flag
                window.decorView.systemUiVisibility = flags
                window.statusBarColor = ContextCompat.getColor(window.context, R.color.colorPrimary)
            }
        }

        fun changeStatusBarColor(window: Window, color: String) {
            window.statusBarColor = Color.parseColor(color)
        }

        fun changeStatusBarColor(window: Window, color: Int) {
            window.statusBarColor = color
        }

        fun setStatusBarMainColor(window: Window, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(window.context, color)
                window.decorView.systemUiVisibility = 0
            }
        }

    }
}