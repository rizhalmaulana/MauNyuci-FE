package com.stp.maunyucibeta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stp.maunyucibeta.databinding.ActivityMainBinding
import com.stp.maunyucibeta.ui.akun.AkunFragment
import com.stp.maunyucibeta.ui.beranda.BerandaFragment
import com.stp.maunyucibeta.ui.layanan.LayananFragment
import com.stp.maunyucibeta.ui.order.OrderFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListener()
        loadFragment(BerandaFragment())
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
}