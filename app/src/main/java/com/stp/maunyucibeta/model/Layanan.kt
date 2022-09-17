package com.stp.maunyucibeta.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Layanan(
    @SerializedName("nama_layanan")
    @Expose
    var nama_layanan: String?,

    @SerializedName("opsi_layanan")
    @Expose
    var opsi_layanan: String?,

    @SerializedName("image_layanan")
    @Expose
    var image_layanan: Int?,

    @SerializedName("jenis_layanan")
    @Expose
    var jenis_layanan: String?,

    @SerializedName("harga_layanan")
    @Expose
    var harga_layanan: String?
)