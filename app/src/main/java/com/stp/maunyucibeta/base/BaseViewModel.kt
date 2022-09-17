package com.stp.maunyucibeta.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import timber.log.Timber

abstract class BaseViewModel() : ViewModel() {

    private val _error: MutableLiveData<Exception?> by lazy {
        MutableLiveData<Exception?>(null)
    }

    val error: MutableLiveData<Exception?>
        get() = _error

    fun showError(message: String?) {
        val exception = Exception(message)
        setError(exception)
        showLogError(exception)
    }

    fun showError(exception: Exception) {
        setError(exception)
        showLogError(exception)
    }

    fun showPostError(message: String?) {
        val exception = Exception(message)
        postError(exception)
        showLogError(exception)
    }

    fun showPostError(exception: Exception) {
        postError(exception)
        showLogError(exception)
    }

    fun MutableLiveData<Exception>.showError(exception: Exception) {
        postValue(exception)
        showLogError(exception)
    }

    private fun showLogError(exception: Exception) {
        Timber.tag(this::class.java.name).e(exception)
    }

    internal fun setError(exception: Exception?) {
        _error.value = exception
    }

    internal fun postError(exception: Exception?) {
        _error.postValue(exception)
    }

    fun <T> mappingObject(objects: Any, clazz: Class<T>?): T {
        val gson = Gson()
        val json = gson.toJson(objects)
        return gson.fromJson(json, clazz)
    }

}