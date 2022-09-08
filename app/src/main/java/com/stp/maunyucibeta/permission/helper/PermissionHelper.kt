package com.stp.maunyucibeta.permission.helper

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.stp.maunyucibeta.permission.listener.PermissionListener
import com.stp.maunyucibeta.permission.model.Permission
import com.stp.maunyucibeta.permission.ui.PermissionFragment

object Constant {
    const val PERMISSIONS_REQUEST_CODE = 42
}

class PermissionHelper private constructor(fragmentManager: FragmentManager) {

    private val tag: String = PermissionFragment::class.java.simpleName

    private val permissionsFragment by lazy { permissionsFragment(fragmentManager) }

    constructor(activity: FragmentActivity) : this(activity.supportFragmentManager)

    constructor(fragment: Fragment) : this(fragment.childFragmentManager)

    fun request(vararg permissions: String?): PermissionHelper {
        val unrequestedPermissions: MutableList<Permission> = ArrayList()
        permissionsFragment?.apply {
            permissionList.clear()
            permissions.forEach {
                it?.let { unrequestedPermissions.add(Permission(it, isGranted(it), false)) }
            }
            unrequestedPermissions.takeIf { it.isNotEmpty() }?.run { requestPermissions(this) }
        }
        return this
    }

    fun requests(permissions: Array<String>): PermissionHelper {
        val unrequestedPermissions: MutableList<Permission> = ArrayList()
        permissionsFragment?.apply {
            permissionList.clear()
            permissions.forEach {
                it.let { unrequestedPermissions.add(Permission(it, isGranted(it), false)) }
            }
            unrequestedPermissions.takeIf { it.isNotEmpty() }?.run { requestPermissions(this) }
        }
        return this
    }

    fun listener(listener: PermissionListener.Request) {
        permissionsFragment?.apply {
            isRequestEach = false
            permissionRequestListener = listener
        }
    }

    fun listenerRequestEach(listener: PermissionListener.RequestEach) {
        permissionsFragment?.apply {
            isRequestEach = true
            permissionRequestEachListener = listener
        }
    }

    private fun permissionsFragment(@NonNull fragmentManager: FragmentManager): PermissionFragment? {
        var permissionsFragment: PermissionFragment? = findPermissionsFragment(fragmentManager)
        if (permissionsFragment == null) {
            permissionsFragment = PermissionFragment()
            fragmentManager
                .beginTransaction()
                .add(permissionsFragment, tag)
                .commitNow()
        }
        return permissionsFragment
    }

    private fun findPermissionsFragment(@NonNull fragmentManager: FragmentManager): PermissionFragment? {
        return fragmentManager.findFragmentByTag(tag) as? PermissionFragment
    }
}