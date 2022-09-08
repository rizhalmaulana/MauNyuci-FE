package com.stp.maunyucibeta.utils

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.stp.maunyucibeta.transition.TransitionType

class FragmentSupportManager<Type : Any> private constructor(private val supportFragmentManager: FragmentManager) {

    private var fragmentId: Int = 0
    private var microFeature: FragmentHelper<Type>? = null

    constructor(
        activity: FragmentActivity,
        microFeature: FragmentHelper<Type>,
        fragmentId: Int = android.R.id.content
    ) : this(activity.supportFragmentManager) {
        this.microFeature = microFeature
        this.fragmentId = fragmentId
    }

    fun show(
        transition: TransitionType = TransitionType.StartSheetNavigationTransition,
        callback: (() -> FragmentCallback<Type>)? = null
    ): FragmentSupportManager<Type> {
        try {
            microFeature?.let { createFragment(it, transition, callback) }
        } catch (exception: IllegalStateException) {
            exception.printStackTrace()
        }
        return this
    }

    private fun createFragment(
        microFeature: FragmentHelper<Type>,
        transition: TransitionType = TransitionType.StartSheetNavigationTransition,
        fragmentCallback: (() -> FragmentCallback<Type>)? = null
    ) {
        microFeature.apply {
            if (!isAlreadyAdded) {
                fragmentCallback?.let { this.fragmentCallback = it.invoke() }
                animation = transition
                with(fragment) {
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(transition.enterTransition, transition.exitTransition)
                        .add(fragmentId, this, tag)
                        .commitNow()
                    isAlreadyAdded = true
                }
            }
        }
    }
}