package com.stp.maunyucibeta.model.layanan

import java.io.Serializable

data class SubLayanan(
    val CreatedAt: String? = "",
    val DeletedAt: String? = "",
    val ID: Int? = 0,
    val UpdatedAt: String? = "",
    val foto_sub_layanan: String? = "",
    val estimasi_sub_layanan: String? = "",
    val estimasi_waktu: Int? = 0,
    val harga_sub_layanan: Int? = 0,
    val layanan_id: String? = "",
    val nama_sub_layanan: String? = "",
    val outlet_id: String? = "",
    val satuan_sub_layanan: String? = "",
    val sub_layanan_id: String? = ""
): Serializable