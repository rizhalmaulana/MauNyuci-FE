package com.stp.maunyucibeta

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    override fun ActivityMainBinding.initializeView(savedInstanceState: Bundle?) {
        setupClickListener()
        loadFragment(BerandaFragment())
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupClickListener()
//        loadFragment(BerandaFragment())
//    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.apply {
            setupClickListener()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }


    private fun setupClickListener() {
        val navView: BottomNavigationView = binding.navView

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
            true
        }
    }

    var doubleBackToExitPressedOnce = false

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