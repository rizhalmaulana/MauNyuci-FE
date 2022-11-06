package com.stp.maunyucibeta.model.layanan

import java.io.Serializable

data class Layanan(
    val CreatedAt: String,
    val DeletedAt: String,
    val UpdatedAt: String,
    val icon_layanan: String,
    val layanan_id: String,
    val nama_layanan: String,
    val outlet: String,
    val outlet_id: String,
    val status_cuci: Boolean,
    val status_kering: Boolean,
    val status_setrika: Boolean,
    val sub_layanan: List<SubLayanan>
): Serializable