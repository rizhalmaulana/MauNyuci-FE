package com.stp.maunyucibeta.helper

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

object Static {
    fun isValidEmail(target: String): Boolean {
        if (target.isEmpty()) return false
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(target).matches()
    }

    fun isValidPassword(password: String): Boolean {
        if (password.isEmpty()) return false

        if (password.all { char -> char.isLetterOrDigit() }) return true
        if (password.all { char -> char.isUpperCase() }) return true
        if (password.all { char -> char.isLowerCase() }) return true

//        if (password.filter { it.isLetterOrDigit() }.firstOrNull() == null) return false
//        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
//        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false

        return true
    }
}