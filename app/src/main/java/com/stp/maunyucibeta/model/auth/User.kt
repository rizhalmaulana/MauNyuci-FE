package com.stp.maunyucibeta.model.auth

import java.io.Serializable

data class User(
    val created_at: String? = "",
    val email: String? = "",
    val name: String? = "",
    val user_id: String? = ""
) : Serializable