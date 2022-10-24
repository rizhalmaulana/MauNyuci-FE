package com.stp.maunyucibeta.model.auth

import java.io.Serializable

data class Token(
    val access_token: String? = "",
    val refresh_token: String? = ""
) : Serializable