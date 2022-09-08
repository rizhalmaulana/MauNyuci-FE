package com.stp.maunyucibeta.transition

import com.stp.maunyucibeta.R


sealed class TransitionType {
    abstract val enterTransition: Int
    abstract val exitTransition: Int

    object StartMainNavigationTransition: TransitionType() {
        override val enterTransition: Int = R.anim.enter_from_right
        override val exitTransition: Int = R.anim.exit_to_left
    }

    object ExitMainNavigationTransition: TransitionType() {
        override val enterTransition: Int = R.anim.exit_to_right
        override val exitTransition: Int = R.anim.enter_from_left
    }

    object StartSheetNavigationTransition: TransitionType() {
        override val enterTransition: Int =  R.anim.slide_in_up
        override val exitTransition: Int = R.anim.slide_out_down
    }

}