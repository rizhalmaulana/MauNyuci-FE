package com.stp.maunyucibeta.utils

import androidx.fragment.app.Fragment
import com.stp.maunyucibeta.transition.TransitionType

interface FragmentHelper<Result> {
    val fragment: Fragment
    var animation: TransitionType
    var fragmentCallback: FragmentCallback<Result>?
    var isAlreadyAdded: Boolean

    fun closeFragment() {
        fragment.parentFragmentManager.beginTransaction().setCustomAnimations(
            animation.enterTransition,
            animation.exitTransition
        ).remove(fragment).commit()
        isAlreadyAdded = false
    }
}