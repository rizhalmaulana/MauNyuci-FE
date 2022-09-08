package com.stp.maunyucibeta.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.InputType
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.google.android.material.textfield.TextInputEditText
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.extension.hideKeyboard
import com.stp.maunyucibeta.utils.BlockingFragment
import com.stp.maunyucibeta.utils.DialogUtils
import com.stp.maunyucibeta.utils.FragmentCallback
import com.stp.maunyucibeta.utils.FragmentSupportManager
import retrofit2.HttpException

abstract class BaseActivity<binding : ViewDataBinding> : CoreActivity<binding>(), NavigationConfig {

    private val blockingFragment by lazy { BlockingFragment() }
    private val blockingSupportManager by lazy {
        FragmentSupportManager(this, blockingFragment)
    }

    protected open fun showBlockingPage(action: (() -> Unit)? = null) {
        hideKeyboard()
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
                    ContextCompat.getDrawable(this, R.drawable.header_orange),
                    action
                )
            }
        }
    }

    fun showError(exception: Exception?, message: CharSequence, action: (() -> Unit)? = null) {
        showError(
            exception,
            message,
            ContextCompat.getDrawable(this, R.drawable.header_orange),
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
                is HttpException -> showBlockingPage(action)
                else -> DialogUtils.showBasicAlertDialog(
                    this,
                    icon,
                    message,
                    onButtonClicked = action
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
    ) = DialogUtils.showBasicAlertDialog(
        this,
        drawable,
        message,
        title,
        note,
        action
    )

//    fun showLoadingDialog(
//        message: CharSequence,
//        title: CharSequence,
//        cancelable: Boolean,
//        action: (() -> Unit)? = null
//    ) = DialogUtils.showBasicLoadingDialog(
//        this,
//        message,
//        title,
//        cancelable,
//        action
//    )

//    fun loadingDialog(
//        message: CharSequence,
//        title: CharSequence,
//        cancelable: Boolean,
//        action: (() -> Unit)? = null
//    ): Dialog = DialogUtils.loadingDialog(
//        this,
//        message,
//        title,
//        cancelable,
//        action
//    )

    override fun setAppbarTitle(title: String) {
        toolbarTitle?.text = title
    }

    fun setStatusBarColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(color)
        }
    }


    fun passwordToggleVisibility(
        icon: ImageView, inputEditText: TextInputEditText, passwordHidden: Boolean
    ): Boolean {
        if (!passwordHidden) {
            icon.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_remove_red_eye_black_24dp
                )
            )
            inputEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            inputEditText.setSelection(inputEditText.length())
        } else {
            icon.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_visibility_off_24
                )
            )
            inputEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            inputEditText.setSelection(inputEditText.length())
        }

        return passwordHidden
    }

//    fun showDialogOptionBottomSheet(
//        description: String,
//        yeButtonClick: (() -> Unit)? = null,
//        noButtonClick: (() -> Unit)? = null,
//        yesTitle: String? = "Ya",
//        noTitle: String? = "Tidak",
//    ) = DialogUtils.showDialogOptionBottomSheet(
//        this,
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
//        this,
//        description,
//        cancelable,
//        yeButtonClick
//    )

//    fun showImageDialog(
//        imageUrl: String,
//        onButtonClick: (() -> Unit)? = null
//    ) = DialogUtils.showImageDialog(
//        this,
//        imageUrl,
//        onButtonClick
//    )

//    fun showImageBottomSheet(
//        imageUrl: String,
//        onButtonClick: (() -> Unit)? = null
//    ) = DialogUtils.showImageBottomSheet(
//        this,
//        imageUrl,
//        onButtonClick
//    )

    fun fromHtml(htmlString: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlString, Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL).toString()
        } else {
            Html.fromHtml(htmlString).toString()
        }
    }

//    fun goToShowImageActivity(url: String) {
//        startActivity(
//            Intent(this, ShowImageActivity::class.java).apply {
//                putExtra(ShowImageActivity.IMAGE_URL, url)
//            }
//        )
//    }
//
//    fun goToWebViewActivity(url: String, title: String = "") {
//        startActivity(
//            Intent(this, WebViewActivity::class.java).apply {
//                putExtra(WebViewActivity.KEY_URL, url)
//                putExtra(WebViewActivity.KEY_TITLE, title)
//            }
//        )
//    }
}