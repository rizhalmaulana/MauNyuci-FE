package com.stp.maunyucibeta.ui.layanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stp.maunyucibeta.base.BaseViewModel
import com.stp.maunyucibeta.exception.GeneralException
import com.stp.maunyucibeta.model.Layanan
import com.stp.maunyucibeta.repository.RemoteRepository
import kotlinx.coroutines.launch

class LayananViewModel(private val remoteRepository: RemoteRepository) : BaseViewModel() {

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading

    private val _data2 = MutableLiveData<Int?>()
    val data2: LiveData<Int?> = _data2

    private val _listLayanan = MutableLiveData<MutableList<Layanan>?>()
    val listLayanan: MutableLiveData<MutableList<Layanan>?>
        get() = _listLayanan

    val errorMessage = MutableLiveData<String>()

    fun resetState() {
        setError(null)
    }

    fun getListDummyLayanan() = viewModelScope.launch {
        _loading.value = true
        try {
            val response = remoteRepository.getLayanan()
            _loading.value = false
            if (response.state) {
                val listLayanan: MutableList<Layanan> = arrayListOf()
                listLayanan.addAll(response.data ?: arrayListOf())
                _listLayanan.value = listLayanan
            } else {
                _listLayanan.value = null
                showError(GeneralException(response.message))
            }
        } catch (exception: Exception) {
            _loading.value = false
            showError(exception)
        }
    }
}