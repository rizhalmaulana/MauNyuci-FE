package com.stp.maunyucibeta.utils

import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.base.CoreFragment
import com.stp.maunyucibeta.databinding.BlockingPageBinding
import com.stp.maunyucibeta.transition.TransitionType
import kotlinx.android.synthetic.main.view_toobar_blocking_page.view.*

class BlockingFragment : CoreFragment<BlockingPageBinding>(), FragmentHelper<Unit> {

    override fun getLayoutId(): Int = R.layout.blocking_page
    override val fragment: Fragment
        get() = this
    override var animation: TransitionType = TransitionType.StartSheetNavigationTransition
    override var fragmentCallback: FragmentCallback<Unit>? = null
    override var isAlreadyAdded: Boolean = false
    override val listenBackPressed: Boolean = true
    override fun onBackPressed() = closeFragment()

    override fun BlockingPageBinding.initializeView() {
        blockingToolbar.closeSheet.setOnClickListener { closeFragment() }
        Glide.with(requireContext())
            .load(R.drawable.ic_blocking)
            .into(starImageView)
        blockingDescriptionTextView.configureDescription()
        retryButton.setOnClickListener {
            closeFragment()
            fragmentCallback?.action()
        }
    }

    private fun AppCompatTextView.configureDescription() {
        movementMethod = LinkMovementMethod.getInstance()
        text = SpannableStringBuilder().append(getString(R.string.sepertinya_kamu_tidak_terkoneksi))
    }
}