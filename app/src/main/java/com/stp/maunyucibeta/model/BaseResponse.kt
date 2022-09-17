package com.stp.maunyucibeta.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @field:SerializedName("state")
    val state: Boolean = false,
    @field:SerializedName("message")
    val message: String = "",
    @field:SerializedName("data")
    val data: T? = null,
)