package com.stp.maunyucibeta.utils

import com.stp.maunyucibeta.BuildConfig


object Constants {
    const val DATE_FORMATTED_LIVE_AT = "dd MMMM yyyy"
    const val URL_PLAY_STORE = "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID
    const val YA = "YA"
    const val TIDAK = "TIDAK"
    const val SPLASH_SCREEN_DELAY_DURATION = 2000L
    const val WHATS_APP_URL = "https://api.whatsapp.com/send?phone=6289662549895&text="
    const val TEXT_PLAIN = "text/plain"
    const val COM_WHATSAPP = "com.whatsapp"
    const val EMAIL_DUMMY_FB = "@sispkujang.id"
    const val HH_mm = "HH mm"
    const val HH_DOT_mm = "HH:mm"
    const val E_MM_DD_yyyy = "E, MM/dd/yyyy"
    const val DD_MMM = "dd MMM"
    const val MM_DD_YYYY = "MM/dd/yyyy"
    const val MM_DD_YYYY_HH_mm = "MM/dd/yyyy HH mm"
    const val MM_DD_YYYY_HH_dot_mm = "MM/dd/yyyy HH:mm"
    const val MM_DD_yyyy_HH_mm_ss = "MM/dd/yyyy HH:mm:ss"
    const val DD_MM_yyyy_HH_mm_ss = "dd/MM/yyyy HH:mm:ss"
    const val DD_MMM_yyyy = "dd/MM/yyyy"
    const val DD_MM_YYYY_HH_DOT_mm = "dd/MM/yyyyHH:mm"
    const val E_DD_MMM_yyyy = "E, dd/MM/yyyy"
    const val HH = "HH"
    const val yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"
    const val yyyy_MM_dd = "yyyy-MM-dd"
    const val MAIL_WAITING = "0"
    const val MAIL_DISPOSISI = "D"
    const val MAIL_REJECT = "C"
    const val MAIL_APPROVE = "A"
    val USER_GROUP_CODE_ACCEPTOR = arrayListOf("1", "2")
    val CAN_SHOW_ANGGARAN = arrayListOf("KETUA_UMUM", "SEKUM", "BENDAHARA", "KETUA_BIDANG")
    val CAN_DELETE_COMMENT = arrayListOf("KETUA_UMUM", "SEKUM")
    val IS_PENGURUS_INTI = arrayListOf("1", "2", "3")
    const val NOTIFICATION_KEY = "NOTIFICATION_KEY"
    const val NOTIFICATION_TYPE = "NOTIFICATION_TYPE"

    const val PUSH_NOTIF_USER = "u_"
    const val PUSH_NOTIF_ROLE = "role_"
    const val PUSH_NOTIF_BIDANG = "bidang_"
    const val PUSH_NOTIF_GROUP = "group_"
    const val PUSH_NOTIF_GLOBAL = "kujang_global"
}