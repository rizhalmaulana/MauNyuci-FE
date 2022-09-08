package com.stp.maunyucibeta.permission.ui

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment
import com.stp.maunyucibeta.permission.helper.Constant.PERMISSIONS_REQUEST_CODE
import com.stp.maunyucibeta.permission.listener.PermissionListener
import com.stp.maunyucibeta.permission.model.Permission

class PermissionFragment : Fragment(), PermissionListener {

    var permissionList: MutableList<Permission> = ArrayList()
    var permissionRequestListener: PermissionListener.Request? = null
    var permissionRequestEachListener: PermissionListener.RequestEach? = null
    var isRequestEach: Boolean = false
    var isRequested: Boolean = false

    private fun combineGranted(permissions: List<Permission>): Boolean =
        permissions.all { it.granted }

    private fun combineShouldShowRequestPermissionRationale(permissions: List<Permission>): Boolean =
        permissions.any { it.shouldShowRequestPermissionRationale }

    fun isGranted(permission: String): Boolean = !isMarshmallow() || granted(permission)

    fun isRevoked(permission: String): Boolean = isMarshmallow() && revoked(permission)

    fun requestPermissions(unrequestedPermissions: MutableList<Permission>) {
        isRequested = true
        val unrequestedPermissionList: MutableList<String> = ArrayList()
        unrequestedPermissions.map { unrequestedPermissionList.add(it.name) }
        request(unrequestedPermissionList.toTypedArray())
    }

    private fun isMarshmallow(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @TargetApi(Build.VERSION_CODES.M)
    private fun granted(permission: String): Boolean = activity
        ?.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    @TargetApi(Build.VERSION_CODES.M)
    private fun revoked(permission: String): Boolean = activity?.packageManager
        ?.isPermissionRevokedByPolicy(permission, activity?.packageName.toString()) == true

    private fun request(unrequestedPermissions: Array<String>) =
        requestPermissions(unrequestedPermissions, PERMISSIONS_REQUEST_CODE)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSIONS_REQUEST_CODE) return
        val shouldShowRequestPermissionRationale = BooleanArray(permissions.size)
        if (isRequestEach) {
            requestForEach(permissions, shouldShowRequestPermissionRationale, grantResults)
        } else {
            request(permissions, shouldShowRequestPermissionRationale, grantResults)
        }
        isRequested = false
    }

    private fun requestForEach(
        permissions: Array<out String>,
        shouldShowRequestPermissionRationale: BooleanArray,
        grantResults: IntArray
    ) {
        permissions.indices.forEach {
            shouldShowRequestPermissionRationale[it] =
                shouldShowRequestPermissionRationale(permissions[it])
            val granted = grantResults[it] == PackageManager.PERMISSION_GRANTED
            permissionList.add(
                Permission(
                    permissions[it],
                    granted,
                    shouldShowRequestPermissionRationale[it]
                )
            )
            permissionRequestEachListener?.run {
                when {
                    isGranted(permissions[it]) -> granted(permissions[it])
                    isRevoked(permissions[it]) -> revoked(permissions[it])
                    else -> denied(permissions[it])
                }
            }
        }
    }

    private fun request(
        permissions: Array<out String>,
        shouldShowRequestPermissionRationale: BooleanArray,
        grantResults: IntArray
    ) {
        permissions.indices.forEach {
            shouldShowRequestPermissionRationale[it] =
                shouldShowRequestPermissionRationale(permissions[it])
            val granted = grantResults[it] == PackageManager.PERMISSION_GRANTED
            permissionList.add(
                Permission(
                    permissions[it],
                    granted,
                    shouldShowRequestPermissionRationale[it]
                )
            )
        }.takeIf { permissionList.size == permissions.size && !isRequestEach }?.run {
            permissionRequestListener?.run {
                when {
                    combineGranted(permissionList) -> granted()
                    combineShouldShowRequestPermissionRationale(permissionList) -> showRequestPermissionRationale()
                    else -> denied()
                }
            }
        }
    }
}