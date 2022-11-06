package com.stp.maunyucibeta.ui.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.base.BaseFragment
import com.stp.maunyucibeta.databinding.FragmentOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_order
    private val viewModel by viewModels<OrderViewModel>()

    override fun FragmentOrderBinding.initializeView() {
        swpeOrder.apply {
            setOnRefreshListener {
                isRefreshing = true
                fetcher()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            swpeOrder.apply {
                setOnRefreshListener {
                    isRefreshing = true
                    fetcher()
                }
            }

            setupUI()
            fetcher()
            setupObserver()
        }
    }

    private fun setupUI() {}

    private fun fetcher() {
        viewModel.apply {
            fetchOrder()
            setupObserver()
        }
    }

    private fun setupObserver() {
        binding.apply {
            viewModel.apply {
                title.observe(viewLifecycleOwner) { it ->
                    it.getContentIfNotHandled()?.let {
                        txtTitleOrder.text = it
                    }
                }

                body.observe(viewLifecycleOwner) { it ->
                    it.getContentIfNotHandled()?.let {
                        txtBodyOrder.text = it
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}