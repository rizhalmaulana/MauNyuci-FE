package com.stp.maunyucibeta.ui.beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.stp.maunyucibeta.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {

    private var _bindingBeranda: FragmentBerandaBinding? = null
    private val binding get() = _bindingBeranda!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[BerandaViewModel::class.java]
        _bindingBeranda = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingBeranda = null
    }
}