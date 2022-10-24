package com.stp.maunyucibeta.ui.beranda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stp.maunyucibeta.base.BaseViewModel
import com.stp.maunyucibeta.data.DummyData
import com.stp.maunyucibeta.data.UserRepository
import com.stp.maunyucibeta.model.beranda.Menu
import com.stp.maunyucibeta.repository.RemoteRepository
import com.stp.maunyucibeta.repository.ResourceRepository
import com.stp.maunyucibeta.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BerandaViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val userRepository: UserRepository,
    private val resourceRepository: ResourceRepository,
    private val preferenceManager: PreferenceManager,
    ) : BaseViewModel() {

    private var menuList: ArrayList<Menu> = arrayListOf()

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading

    private val _menu = MutableLiveData<MutableList<Menu>?>()
    val menu: LiveData<MutableList<Menu>?>
        get() = _menu

    fun resetState() {
        _menu.value = arrayListOf()
        setError(null)
    }

    fun fetchBeranda() = viewModelScope.launch {
        _loading.value = true
        menuList.addAll(DummyData.listDummyMenuBeranda)

        _menu.value = menuList
    }

    fun getUserPreference() = preferenceManager.getUser()
}