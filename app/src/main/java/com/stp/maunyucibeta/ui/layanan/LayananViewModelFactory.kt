package com.stp.maunyucibeta.ui.layanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stp.maunyucibeta.repository.RemoteRepository

class LayananViewModelFactory(private val remoteRepository: RemoteRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LayananViewModel::class.java)) {
            LayananViewModel(this.remoteRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}