package com.stp.maunyucibeta.model.layanan

import com.google.gson.annotations.SerializedName

data class Layanan(
    @SerializedName("created_at")
    val CreatedAt: String,
    @SerializedName("deleted_at")
    val DeletedAt: String,
    @SerializedName("id")
    val ID: Int,
    @SerializedName("updated_at")
    val UpdatedAt: String,
    @SerializedName("icon_layanan")
    val icon_layanan: String,
    @SerializedName("layanan_id")
    val layanan_id: String,
    @SerializedName("nama_layanan")
    val nama_layanan: String,
    @SerializedName("outlet")
    val outlet: String = "",
    @SerializedName("outlet_id")
    val outlet_id: String,
    @SerializedName("status_cuci")
    val status_cuci: Boolean,
    @SerializedName("status_kering")
    val status_kering: Boolean,
    @SerializedName("status_setrika")
    val status_setrika: Boolean,
    @SerializedName("sub_layanan")
    val sub_layanan: List<SubLayanan>
)