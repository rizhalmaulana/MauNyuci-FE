package com.stp.maunyucibeta.utils

interface FragmentCallback<T> {
    fun result(result: T) {}
    fun action() {}
}