package com.stp.maunyucibeta.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stp.maunyucibeta.base.BaseViewModel
import com.stp.maunyucibeta.data.UserRepository
import com.stp.maunyucibeta.helper.Event
import com.stp.maunyucibeta.repository.RemoteRepository
import com.stp.maunyucibeta.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val userRepository: UserRepository,
    private val preferenceManager: PreferenceManager,
): BaseViewModel() {

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }
    val loading: MutableLiveData<Boolean>
        get() = _loading

    private val _title = MutableLiveData<Event<String>>()
    val title: LiveData<Event<String>>
        get() = _title

    private val _body = MutableLiveData<Event<String>>()
    val body: LiveData<Event<String>>
        get() = _body

    fun fetchOrder() = viewModelScope.launch {
        _loading.value = true

        _title.value = Event("Order")
        _body.value = Event("Halaman Order")
    }
}