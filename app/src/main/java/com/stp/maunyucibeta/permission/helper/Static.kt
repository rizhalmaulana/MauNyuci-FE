package com.stp.maunyucibeta.permission.helper

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

class Static {
    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}