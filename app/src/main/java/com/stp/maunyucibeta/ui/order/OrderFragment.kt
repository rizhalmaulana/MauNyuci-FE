package com.stp.maunyucibeta.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.stp.maunyucibeta.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private var _bindingOrder: FragmentOrderBinding? = null
    private val binding get() = _bindingOrder!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val orderiewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        _bindingOrder = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingOrder = null
    }
}