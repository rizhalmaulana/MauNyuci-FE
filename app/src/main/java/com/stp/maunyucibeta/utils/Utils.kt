package com.stp.maunyucibeta.utils

import java.text.NumberFormat


object Utils {

    fun Any.convertRupiah(): kotlin.String? {
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 0

        return formatter.format(this)
    }

    object String {
        const val EMPTY = ""
    }
}