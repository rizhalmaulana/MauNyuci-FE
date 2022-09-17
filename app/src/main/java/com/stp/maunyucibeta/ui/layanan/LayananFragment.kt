package com.stp.maunyucibeta.ui.layanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.stp.maunyucibeta.adapter.LayananAdapter
import com.stp.maunyucibeta.data.DummyData
import com.stp.maunyucibeta.databinding.FragmentLayananBinding
import com.stp.maunyucibeta.model.DummyLayanan
import com.stp.maunyucibeta.repository.RemoteRepository
import com.stp.maunyucibeta.repository.RemoteRepositoryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LayananFragment : Fragment() {

    private lateinit var binding: FragmentLayananBinding
    private lateinit var layananViewModel: LayananViewModel
    private lateinit var layananAdapter: LayananAdapter

    private lateinit var shimmerEffect: ShimmerFrameLayout

    private val remoteService = RemoteRepositoryService.getInstance()
    private var listLayanan: ArrayList<DummyLayanan> = arrayListOf()

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layananViewModel = activity?.run {
            ViewModelProvider(
                requireActivity(),
                LayananViewModelFactory(RemoteRepository(remoteService))
            )[LayananViewModel::class.java]
        } ?: throw Exception("Invalid Fragment Layanan")
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
    }

    private fun fetchLaunch() {
        activityScope.launch {
            delay(2000)
            fetchLayanan()
        }

        binding.rvLayanan.visibility = GONE

        shimmerEffect.startShimmer()
        shimmerEffect.visibility = VISIBLE
    }

    private fun fetchLayanan() {
        listLayanan.addAll(DummyData.listDummyLayanan)
        layananAdapter = LayananAdapter(listLayanan)

        binding.rvLayanan.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = layananAdapter
            setHasFixedSize(true)
        }

        binding.rvLayanan.visibility = VISIBLE

        shimmerEffect.stopShimmer()
        shimmerEffect.visibility = GONE

        binding.swpeLayanan.isRefreshing = false

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}