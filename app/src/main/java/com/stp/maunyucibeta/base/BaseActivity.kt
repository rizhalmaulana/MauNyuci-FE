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
import androidx.core.text.HtmlCompat
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

    fun loadingDialog(
        title: CharSequence,
        cancelable: Boolean,
        action: (() -> Unit)? = null
    ): Dialog = DialogUtils.loadingDialog(
        this,
        title,
        cancelable,
        action
    )

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

    fun fromHtml(htmlString: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlString, Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL).toString()
        } else {
            HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        }
    }
}