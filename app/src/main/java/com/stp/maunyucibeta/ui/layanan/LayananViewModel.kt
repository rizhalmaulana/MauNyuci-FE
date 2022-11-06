package com.stp.maunyucibeta.ui.layanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stp.maunyucibeta.base.BaseViewModel
import com.stp.maunyucibeta.data.UserRepository
import com.stp.maunyucibeta.helper.Event
import com.stp.maunyucibeta.model.layanan.Layanan
import com.stp.maunyucibeta.model.layanan.SubLayanan
import com.stp.maunyucibeta.repository.RemoteRepository
import com.stp.maunyucibeta.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class LayananViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val userRepository: UserRepository,
    private val preferenceManager: PreferenceManager,
) :
    BaseViewModel() {

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading

    private val _layanan = MutableLiveData<MutableList<Layanan>?>()
    val layanan: LiveData<MutableList<Layanan>?>
        get() = _layanan

    private val _statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _statusMessage

    fun resetState() {
        _layanan.value = arrayListOf()
        setError(null)
    }

    fun fetchLayanan() = viewModelScope.launch {
        _loading.value = true
        try {
            val response = remoteRepository.getLayanan()
            if (response.state) {
                val listLayanan: MutableList<Layanan> = arrayListOf()
                response.data?.let { listLayanan.addAll(it) }

                _layanan.value = listLayanan
                _loading.value = false
            } else {
                _loading.value = false
                _layanan.value = null
                _statusMessage.value = Event("Kamu gagal dapatkan data layanan, coba lagi!")
            }
        } catch (exception: Exception) {
            _loading.value = false
            _statusMessage.value = Event("Yah, gagal dapatkan layanan. Coba lagi!")
        } catch (exceptionInet: ConnectException) {
            _loading.value = false
            _statusMessage.value = Event("Yah, gagal dapatkan layanan. Cek koneksimu!")
        }
    }
}
