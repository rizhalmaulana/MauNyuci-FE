package com.stp.maunyucibeta.model.layanan

import com.google.gson.annotations.SerializedName

data class SubLayanan(
    @SerializedName("created_at")
    val CreatedAt: String,
    @SerializedName("deleted_at")
    val DeletedAt: String,
    @SerializedName("id")
    val ID: Int,
    @SerializedName("updated_at")
    val UpdatedAt: String,
    @SerializedName("estimasi_sub_layanan")
    val estimasi_sub_layanan: String,
    @SerializedName("estimasi_waktu")
    val estimasi_waktu: Int,
    @SerializedName("harga_sub_layanan")
    val harga_sub_layanan: Int,
    @SerializedName("layanan_id")
    val layanan_id: String,
    @SerializedName("nama_sub_layanan")
    val nama_sub_layanan: String,
    @SerializedName("outlet_id")
    val outlet_id: String,
    @SerializedName("satuan_sub_layanan")
    val satuan_sub_layanan: String,
    @SerializedName("sub_layanan_id")
    val sub_layanan_id: String
)