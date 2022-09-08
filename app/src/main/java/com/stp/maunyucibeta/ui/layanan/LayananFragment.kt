package com.stp.maunyucibeta.ui.layanan

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stp.maunyucibeta.R

class LayananFragment : Fragment() {

    companion object {
        fun newInstance() = LayananFragment()
    }

    private lateinit var viewModel: LayananViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layanan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LayananViewModel::class.java)
        // TODO: Use the ViewModel
    }

}