package com.stp.maunyucibeta.ui.beranda

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.adapter.RecyclerViewAdapter
import com.stp.maunyucibeta.base.BaseFragment
import com.stp.maunyucibeta.databinding.FragmentBerandaBinding
import com.stp.maunyucibeta.databinding.ViewItemOmsetBinding
import com.stp.maunyucibeta.model.beranda.Menu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BerandaFragment : BaseFragment<FragmentBerandaBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_beranda
    private lateinit var berandaViewModel: BerandaViewModel

    private lateinit var shimmerEffect: ShimmerFrameLayout
    private lateinit var recyclerMenuViewAdapter: RecyclerViewAdapter<Menu, ViewItemOmsetBinding>

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBerandaBinding.inflate(inflater, container, false)
        binding.viewModelBeranda = berandaViewModel
        binding.lifecycleOwner = this

        return binding.root
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
        setupObserver()
        fetcher()
    }

    private fun fetcher() {
        binding.apply {
            berandaViewModel.apply {
                resetState()
                fetchBeranda()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserver() {
        binding.apply {
            berandaViewModel.apply {
                loading.observe(viewLifecycleOwner) {
                    if (it) {
                        binding.apply {
                            activityScope.launch {
                                delay(2000)
                            }

                            cvOmset.visibility = View.GONE

                            shimmerEffect.startShimmer()
                            shimmerEffect.visibility = View.VISIBLE
                        }
                    } else {
                        binding.apply {
                            cvOmset.visibility = View.VISIBLE

                            shimmerEffect.stopShimmer()
                            shimmerEffect.visibility = View.GONE

                            swipeContainerBeranda.isRefreshing = false
                        }
                    }
                }

                error.observe(viewLifecycleOwner) {
                    if (it != null) {
                        timber.log.Timber.tag(this::class.java.name).e(it)
                        showError(it) {
                            fetcher()
                        }
                    }
                }

                menu.observe(viewLifecycleOwner) {
                    it?.let { recyclerMenuViewAdapter.updateList(it) }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "LogNotTimber")
    private fun setupUI() {
        binding.apply {
            setupMenuAdapter()
            val user = berandaViewModel.getUserPreference()

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

                itemView.txtOmsetToday.text = itemModel.omset.toString()
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