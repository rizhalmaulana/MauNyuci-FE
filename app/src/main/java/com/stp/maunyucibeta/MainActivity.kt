package com.stp.maunyucibeta

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.stp.maunyucibeta.base.BaseActivity
import com.stp.maunyucibeta.databinding.ActivityMainBinding
import com.stp.maunyucibeta.extension.showToast
import com.stp.maunyucibeta.ui.akun.AkunFragment
import com.stp.maunyucibeta.ui.beranda.BerandaFragment
import com.stp.maunyucibeta.ui.layanan.LayananFragment
import com.stp.maunyucibeta.ui.order.OrderFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main
    var doubleBackToExitPressedOnce = false

    override fun ActivityMainBinding.initializeView(savedInstanceState: Bundle?) {
        setupBottomNavigationBar()
        loadFragment(BerandaFragment())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.apply {
            setupBottomNavigationBar()
        }
    }

    private fun setupBottomNavigationBar() {
        binding.apply {
            navView.setOnItemSelectedListener {
                val fragment = when (it.itemId) {
                    R.id.navigation_beranda -> {
                        BerandaFragment()
                    }
                    R.id.navigation_order -> {
                        OrderFragment()
                    }
                    R.id.navigation_layanan -> {
                        LayananFragment()
                    }
                    R.id.navigation_akun -> {
                        AkunFragment()
                    }
                    else -> {
                        BerandaFragment()
                    }
                }

                loadFragment(fragment)
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        with(binding.navView) {
            if (this.selectedItemId == 0) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }
                doubleBackToExitPressedOnce = true
                showToast("Please click Back again to exit")
                Handler(Looper.getMainLooper()).postDelayed(
                    { doubleBackToExitPressedOnce = false },
                    2000
                )
            } else {
                selectedItemId = 0
            }
        }
    }
}