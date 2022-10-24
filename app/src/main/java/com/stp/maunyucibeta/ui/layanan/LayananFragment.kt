package com.stp.maunyucibeta.ui.layanan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.adapter.RecyclerViewAdapter
import com.stp.maunyucibeta.databinding.FragmentLayananBinding
import com.stp.maunyucibeta.databinding.ViewItemLayananBinding
import com.stp.maunyucibeta.model.layanan.Layanan
import com.stp.maunyucibeta.repository.RemoteRepository
import com.stp.maunyucibeta.repository.RemoteRepositoryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LayananFragment : Fragment() {

    private lateinit var binding: FragmentLayananBinding
    private lateinit var layananViewModel: LayananViewModel
    private lateinit var recyclerLayanan: RecyclerViewAdapter<Layanan, ViewItemLayananBinding>
    private lateinit var shimmerEffect: ShimmerFrameLayout

    private val remoteService = RemoteRepositoryService.getInstance()
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        layananViewModel = activity?.run {
//            ViewModelProvider(
//                requireActivity(),
//                LayananViewModelFactory(RemoteRepository(remoteService))
//            )[LayananViewModel::class.java]
//        } ?: throw Exception("Invalid Fragment Layanan")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLayananBinding.inflate(inflater, container, false)
        binding.viewModelLayanan = layananViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmerEffect = binding.shmrLayanan

        binding.swpeLayanan.apply {
            setOnRefreshListener {
                isRefreshing = true
                fetchLaunch()
            }
        }

        fetchLaunch()
        setupObserver()
    }

    private fun setupObserver() {
        layananViewModel.apply {
            loading.observe(viewLifecycleOwner) {
                if (it) {
                    shimmerEffect.startShimmer()
                    binding.shmrLayanan.visibility = VISIBLE
                    binding.rvLayanan.visibility = GONE
                } else {
                    shimmerEffect.stopShimmer()
                    binding.shmrLayanan.visibility = GONE
                    binding.rvLayanan.visibility = VISIBLE
                    binding.swpeLayanan.isRefreshing = false
                }
            }

            layanan.observe(viewLifecycleOwner) {
                if (it != null) {
                    recyclerLayanan.list = it
                }
            }
        }
    }

    private fun fetchLaunch() {
        activityScope.launch {
            delay(2000)

            layananViewModel.apply {
                resetState()
                fetchLayanan()
            }

            setupLayananAdapter()
        }

//        binding.rvLayanan.visibility = GONE
//
//        shimmerEffect.startShimmer()
//        shimmerEffect.visibility = VISIBLE
    }

    private fun setupLayananAdapter() {
        recyclerLayanan = RecyclerViewAdapter(
            arrayListOf(),
            R.layout.view_item_layanan
        ) { itemView, itemModel ->
            itemView.txtItemNamaLayanan.text = itemModel.nama_layanan

            when (itemModel.status_cuci) {
                true -> {
                    itemView.txtItemOpsiCuci.text = "Cuci"
                    itemView.ivArrowRightFirst.visibility = VISIBLE
                }
                false -> {
                    itemView.txtItemOpsiCuci.text = ""
                    itemView.ivArrowRightFirst.visibility = GONE
                }
            }

            when (itemModel.status_kering) {
                true -> {
                    itemView.txtItemOpsiKering.text = "Kering"
                    itemView.ivArrowRightSecond.visibility = VISIBLE
                }
                false -> {
                    itemView.txtItemOpsiKering.text = ""
                    itemView.ivArrowRightSecond.visibility = GONE
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

            if (itemModel.icon_layanan != "") {
                Glide.with(requireActivity())
                    .load(itemModel.icon_layanan)
                    .override(50, 50)
                    .into(itemView.ivItemGambarLayanan)
            } else {
                Glide.with(requireActivity())
                    .load(R.drawable.g_kiloan)
                    .override(50, 50)
                    .into(itemView.ivItemGambarLayanan)
            }

        }

        val linearLayoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.rvLayanan.apply {
            layoutManager = linearLayoutManager
            adapter = recyclerLayanan
            isNestedScrollingEnabled = false
        }

//        binding.rvLayanan.visibility = VISIBLE
//
//        shimmerEffect.stopShimmer()
//        shimmerEffect.visibility = GONE
//
//        binding.swpeLayanan.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    companion object {
        private const val TAG = "LayananFragment"
    }
}
