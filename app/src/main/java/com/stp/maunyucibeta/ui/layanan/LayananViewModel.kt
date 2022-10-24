package com.stp.maunyucibeta.ui.layanan

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stp.maunyucibeta.base.BaseViewModel
import com.stp.maunyucibeta.exception.GeneralException
import com.stp.maunyucibeta.model.layanan.Layanan
import com.stp.maunyucibeta.repository.RemoteRepository
import kotlinx.coroutines.launch

class LayananViewModel(private val remoteRepository: RemoteRepository) : BaseViewModel() {

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading

    private val _layanan = MutableLiveData<MutableList<Layanan>?>()
    val layanan: LiveData<MutableList<Layanan>?>
        get() = _layanan

    fun resetState() {
        setError(null)
    }

    fun fetchLayanan() = viewModelScope.launch {
        _loading.value = true
        try {
            val response = remoteRepository.getLayanan()
            if (response.state && response.data != null) {
                _loading.value = false

                val listLayanan = mutableListOf<Layanan>()
                listLayanan.addAll(response.data)
                _layanan.value = listLayanan

            } else {
                _loading.value = false
                _layanan.value = null
                showError(GeneralException(response.message))
            }
        } catch (exception: Exception) {
            _loading.value = false
            showError(exception)
        }

    }

}
