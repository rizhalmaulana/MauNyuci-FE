package com.stp.maunyucibeta.model.auth

import java.io.Serializable

data class Outlet(
    val CreatedAt: String = "",
    val DeletedAt: String = "",
    val ID: Int = 0,
    val UpdatedAt: String = "",
    val outlet_data: String = "",
    val outlet_id: String = "",
    val nama_outlet: String = "",
    val alamat_outlet: String = "",
    val foto_outlet: String = "",
    val foto_outlet_url: String = "",
    val outlet_utama: Boolean,
    val user_data: String? = "",
): Serializable