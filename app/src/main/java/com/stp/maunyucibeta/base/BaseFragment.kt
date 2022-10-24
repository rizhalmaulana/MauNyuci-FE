package com.stp.maunyucibeta.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.ViewDataBinding
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.extension.bold
import com.stp.maunyucibeta.extension.color
import com.stp.maunyucibeta.extension.hideKeyboard
import com.stp.maunyucibeta.utils.BlockingFragment
import com.stp.maunyucibeta.utils.DialogUtils
import com.stp.maunyucibeta.utils.FragmentCallback
import com.stp.maunyucibeta.utils.FragmentSupportManager
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseFragment<binding : ViewDataBinding> : CoreFragment<binding>() {

    private var _navigationConfig: NavigationConfig? = null
    protected val navigationConfig get() = _navigationConfig!!
    private val blockingFragment by lazy { BlockingFragment() }
    private val blockingSupportManager by lazy {
        FragmentSupportManager(
            requireActivity(),
            blockingFragment
        )
    }

    protected open val paid: SpannableStringBuilder by lazy {
        SpannableStringBuilder()
            .append(
                getString(R.string.app_name).color(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorPrimary
                    )
                ).bold()
            )
    }

    protected val onProgress: SpannableStringBuilder by lazy {
        SpannableStringBuilder()
            .append(
                getString(R.string.app_name).color(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorDone
                    )
                ).bold()
            )
    }

    protected val waitingPayment: SpannableStringBuilder by lazy {
        SpannableStringBuilder()
            .append(
                getString(R.string.app_name).replace("\n", " ").color(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorProces
                    )
                ).bold()
            )
    }

    protected val waitingConfirmation: SpannableStringBuilder by lazy {
        SpannableStringBuilder()
            .append(
                getString(R.string.app_name).color(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorDone
                    )
                ).bold()
            )
    }

    protected val notPaid: SpannableStringBuilder by lazy {
        SpannableStringBuilder()
            .append(
                getString(R.string.app_name).replace("\n", " ").color(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorWait
                    )
                ).bold()
            )
    }

    protected val rejected: SpannableStringBuilder by lazy {
        SpannableStringBuilder()
            .append(
                getString(R.string.app_name).replace("\n", " ").color(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorWait
                    )
                ).bold()
            )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _navigationConfig = context as NavigationConfig
    }

    override fun onDetach() {
        super.onDetach()
        _navigationConfig = null
    }

    protected open fun showBlockingPage(action: (() -> Unit)? = null) {
        requireActivity().hideKeyboard()
        blockingSupportManager.show {
            object : FragmentCallback<Unit> {
                override fun action() {
                    action?.invoke()
                }
            }
        }
    }

    fun showError(exception: Exception?, action: (() -> Unit)? = null) {
        exception?.let {
            it.message?.let { message ->
                showError(
                    it,
                    message,
                    ContextCompat.getDrawable(requireContext(), R.drawable.logo_white),
                    action
                )
            }
        }
    }

    fun showError(exception: Exception?, message: CharSequence, action: (() -> Unit)? = null) {
        showError(
            exception,
            message,
            ContextCompat.getDrawable(requireContext(), R.drawable.logo_white),
            action
        )
    }

    fun showError(
        exception: Exception?,
        message: CharSequence,
        icon: Drawable?,
        action: (() -> Unit)? = null
    ) {
        exception?.let {
            when (it) {
                is SocketTimeoutException, is UnknownHostException, is ConnectionShutdownException, is IOException, is IllegalStateException -> showBlockingPage(
                    action
                )
                else -> DialogUtils.showBasicAlertDialog(
                    requireActivity(),
                    icon,
                    message
                )
            }
        }
    }

    fun showDialog(
        message: CharSequence,
        drawable: Drawable? = null,
        title: CharSequence? = "",
        note: CharSequence? = "",
        action: (() -> Unit)? = null
    ) =
        DialogUtils.showBasicAlertDialog(
            requireActivity(),
            drawable,
            message,
            title,
            note,
            action
        )

//    fun showImageDialog(
//        imageUrl: String,
//        onButtonClick: (() -> Unit)? = null
//    ) = DialogUtils.showImageDialog(
//        requireActivity(),
//        imageUrl,
//        onButtonClick
//    )

//    fun showDialogOptionBottomSheet(
//        description: String,
//        yeButtonClick: (() -> Unit)? = null,
//        noButtonClick: (() -> Unit)? = null,
//        yesTitle: String? = "Ya",
//        noTitle: String? = "Tidak",
//    ) = DialogUtils.showDialogOptionBottomSheet(
//        requireActivity(),
//        description,
//        yesTitle,
//        noTitle,
//        yeButtonClick,
//        noButtonClick
//    )

//    fun showMessageBottomSheet(
//        description: String,
//        cancelable: Boolean = true,
//        yeButtonClick: (() -> Unit)? = null,
//    ) = DialogUtils.showMessageBottomSheet(
//        requireActivity(),
//        description,
//        cancelable,
//        yeButtonClick
//    )
//
//    fun showImageBottomSheet(
//        imageUrl: String,
//        onButtonCloseClicked: (() -> Unit)? = null,
//        onImageClicked: (() -> Unit)? = null
//    ) = DialogUtils.showImageBottomSheet(
//        requireActivity(),
//        imageUrl,
//        onButtonCloseClicked,
//        onImageClicked
//    )

    fun isBetween(x: Int, lower: Int, upper: Int): Boolean {
        return x in lower..upper
    }


//    fun goToShowImageActivity(url: String) {
//        startActivity(
//            Intent(requireContext(), ShowImageActivity::class.java).apply {
//                putExtra(ShowImageActivity.IMAGE_URL, url)
//            }
//        )
//    }
//
//    fun goToWebViewActivity(url: String, title: String = "") {
//        startActivity(
//            Intent(requireContext(), WebViewActivity::class.java).apply {
//                putExtra(WebViewActivity.KEY_URL, url)
//                putExtra(WebViewActivity.KEY_TITLE, title)
//            }
//        )
//    }

    fun fromHtml(htmlString: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        } else {
            HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        }
    }
}