package com.stp.maunyucibeta.model.auth

import java.io.Serializable

data class Login(
    val `data`: Data,
    val token: Token,
    val code: Int? = 0,
    val error: String? = "",
    val msg: String? = "",
    val state: Boolean? = null,
): Serializable