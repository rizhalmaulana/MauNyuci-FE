package com.stp.maunyucibeta.permission.listener

interface PermissionListener {

    interface Request {
        fun granted()
        fun showRequestPermissionRationale() {}
        fun denied() {}
    }

    interface RequestEach {
        fun granted(permission: String)
        fun revoked(permission: String) {}
        fun denied(permission: String) {}
    }
}