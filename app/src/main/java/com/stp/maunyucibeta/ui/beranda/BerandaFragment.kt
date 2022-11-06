package com.stp.maunyucibeta.ui.beranda

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.adapter.RecyclerViewAdapter
import com.stp.maunyucibeta.base.BaseFragment
import com.stp.maunyucibeta.databinding.FragmentBerandaBinding
import com.stp.maunyucibeta.databinding.ViewItemOmsetBinding
import com.stp.maunyucibeta.model.beranda.Menu
import com.stp.maunyucibeta.utils.Constants
import com.stp.maunyucibeta.utils.Utils.convertRupiah
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BerandaFragment : BaseFragment<FragmentBerandaBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_beranda
    private val viewModel by viewModels<BerandaViewModel>()

    private lateinit var recyclerMenuViewAdapter: RecyclerViewAdapter<Menu, ViewItemOmsetBinding>

    override fun FragmentBerandaBinding.initializeView() {
        swipeContainerBeranda.apply {
            setOnRefreshListener {
                isRefreshing = true
                fetcher()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            swipeContainerBeranda.apply {
                setOnRefreshListener {
                    isRefreshing = true
                    fetcher()
                }
            }
        }

        setupUI()
        fetcher()
    }

    private fun fetcher() {
        binding.apply {
            viewModel.apply {
                fetchBeranda()
                setupObserver()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserver() {
        viewModel.apply {
            binding.apply {
                loading.observe {
                    this?.let {
                        Log.i("Beranda", "observer loading: $it")

                        if (it) {
                            cvOmset.visibility = View.GONE

                            shmrLayanan.startShimmer()
                            shmrLayanan.visibility = View.VISIBLE
                        } else {
                            cvOmset.visibility = View.VISIBLE

                            shmrLayanan.stopShimmer()
                            shmrLayanan.visibility = View.GONE
                        }
                        swipeContainerBeranda.isRefreshing = false
                    }
                }

                error.observe {
                    this?.let {
                        showError(it) {
                            fetcher()
                        }
                    }
                }

                menu.observe {
                    this?.let {
                        viewModelScope.launch {
                            delay(Constants.LOGIN_DELAY_DURATION)

                            shmrLayanan.visibility = View.GONE
                            shmrLayanan.stopShimmer()

                            cvOmset.visibility = View.VISIBLE
                        }
                        recyclerMenuViewAdapter.updateList(it)

                        shmrLayanan.startShimmer()
                        shmrLayanan.visibility = View.VISIBLE

                        cvOmset.visibility = View.GONE
                    }
                    swipeContainerBeranda.isRefreshing = false
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "LogNotTimber")
    private fun setupUI() {
        binding.apply {
            setupMenuAdapter()
            val user = viewModel.getUserPreference()

            if (user != null) {
                txtNamaLaundry.text = user.name
            } else {
                Log.i("BerandaFragment", "setupUI: ${user.toString()}")
                txtNamaLaundry.text = "Outlet Name"
            }

            Glide.with(requireActivity())
                .load(R.drawable.ic_avatar)
                .override(70, 70)
                .into(civFotoProfil)
        }
    }

    private fun setupMenuAdapter() {
        binding.apply {
            recyclerMenuViewAdapter = RecyclerViewAdapter(
                arrayListOf(),
                R.layout.view_item_omset,
            ) { itemView, itemModel ->

                itemView.txtOmsetToday.text = itemModel.omset.convertRupiah().toString()
                itemView.txtValueMasuk.text = itemModel.masuk.toString()
                itemView.txtValueSiapambil.text = itemModel.ambil.toString()
                itemView.txtValueSelesai.text = itemModel.selesai.toString()
            }

            val layoutManagers = LinearLayoutManager(requireContext())
            rvOmset.apply {
                layoutManager = layoutManagers
                adapter = recyclerMenuViewAdapter
                isNestedScrollingEnabled = false
            }
        }
    }
}