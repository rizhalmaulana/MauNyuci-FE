package com.stp.maunyucibeta.model.layanan

import com.google.gson.annotations.SerializedName

data class LayananResponse(
    @SerializedName("data")
    val `data`: List<Layanan>,
    @SerializedName("message")
    val message: String,
    @SerializedName("state")
    val state: Boolean
)