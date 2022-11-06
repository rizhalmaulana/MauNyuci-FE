package com.stp.maunyucibeta.ui.layanan

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.adapter.RecyclerViewAdapter
import com.stp.maunyucibeta.adapter.RecyclerViewSubLayanan
import com.stp.maunyucibeta.base.BaseFragment
import com.stp.maunyucibeta.databinding.FragmentLayananBinding
import com.stp.maunyucibeta.databinding.ViewItemLayananBinding
import com.stp.maunyucibeta.extension.gone
import com.stp.maunyucibeta.extension.visible
import com.stp.maunyucibeta.model.layanan.Layanan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LayananFragment : BaseFragment<FragmentLayananBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_layanan
    private val viewModel by viewModels<LayananViewModel>()

    private lateinit var recyclerLayanan: RecyclerViewAdapter<Layanan, ViewItemLayananBinding>
    private lateinit var recyclerSubLayanan: RecyclerViewSubLayanan

    override fun FragmentLayananBinding.initializeView() {
        binding.apply {
            swpeLayanan.apply {
                setOnRefreshListener {
                    isRefreshing = true
                    fetcher()
                }
            }
            setupUI()
            setupObserver()
            fetcher()
        }
    }

    private fun FragmentLayananBinding.setupUI() {
        binding.apply {
            setupLayananAdapter()
            swpeLayanan.apply {
                setOnRefreshListener {
                    isRefreshing = true
                    fetcher()
                }
            }
        }
    }

    private fun fetcher() {
        viewModel.apply {
            resetState()
            fetchLayanan()
        }
    }

    private fun FragmentLayananBinding.setupObserver() {
        viewModel.apply {
            binding.apply {
                loading.observe {
                    this?.let {
                        if (it) {
                            shmrLayanan.startShimmer()
                            shmrLayanan.visible()

                            rvLayanan.gone()
                        } else {
                            shmrLayanan.stopShimmer()
                            shmrLayanan.gone()

                            rvLayanan.visible()
                        }
                        swpeLayanan.isRefreshing = false
                    }
                }

                layanan.observe {
                    this?.let {
                        recyclerLayanan.updateList(it)

                        it.forEachIndexed { _, element ->
                            recyclerSubLayanan.updateSubLayanan(element.sub_layanan)
                        }
                    }
                }
            }
        }
    }

    private fun FragmentLayananBinding.setupLayananAdapter() {
        recyclerLayanan = RecyclerViewAdapter(
            arrayListOf(),
            R.layout.view_item_layanan
        ) { itemView, itemModel ->
            itemView.txtItemNamaLayanan.text = itemModel.nama_layanan

            when (itemModel.status_cuci) {
                true -> {
                    itemView.txtItemOpsiCuci.text = "Cuci"
                    itemView.ivArrowRightFirst.visible()
                }
                false -> {
                    itemView.txtItemOpsiCuci.text = ""
                    itemView.ivArrowRightFirst.gone()
                }
            }

            when (itemModel.status_kering) {
                true -> {
                    itemView.txtItemOpsiKering.text = "Kering"
                    itemView.ivArrowRightSecond.visible()
                }
                false -> {
                    itemView.txtItemOpsiKering.text = ""
                    itemView.ivArrowRightSecond.gone()
                }
            }

            when (itemModel.status_setrika) {
                true -> {
                    itemView.txtItemOpsiSetrika.text = "Setrika"
                }
                false -> {
                    itemView.txtItemOpsiSetrika.text = ""
                }
            }

            val layoutManagers = LinearLayoutManager(requireContext())
            itemView.rvSubLayanan.apply {
                layoutManager = layoutManagers
                adapter = RecyclerViewSubLayanan(itemModel.sub_layanan)
            }

            Log.i("Layanan", "size sub layanan: ${itemModel.sub_layanan.size}")
        }

        val layoutManagers = LinearLayoutManager(requireContext())
        rvLayanan.apply {
            layoutManager = layoutManagers
            adapter = recyclerLayanan
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}
