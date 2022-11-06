package com.stp.maunyucibeta.model.layanan

import java.io.Serializable

data class LayananResponse(
    val `data`: List<Layanan>?,
    val message: String? = "",
    val state: Boolean
): Serializable