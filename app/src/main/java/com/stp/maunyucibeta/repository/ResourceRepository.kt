package com.stp.maunyucibeta.repository

import android.content.Context

class ResourceRepository(
    private var context: Context
) {
    fun getString(resId: Int): String? {
        return context.getString(resId)
    }
}