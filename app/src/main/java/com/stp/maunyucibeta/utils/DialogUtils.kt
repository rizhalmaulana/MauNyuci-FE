package com.stp.maunyucibeta.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.stp.maunyucibeta.R

class DialogUtils {
    companion object {
        fun showBasicAlertDialog(
            activity: Activity,
            icon: Drawable?,
            message: CharSequence,
            title: CharSequence? = "",
            note: CharSequence? = "",
            onButtonClicked: (() -> Unit)? = null
        ) {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.view_basic_alert_dialog)

            val iconView = dialog.findViewById<ImageView>(R.id.iv_icon_basic_alert)
            if (icon != null) {
                iconView.setImageDrawable(icon)
            } else {
                iconView.visibility = View.GONE
            }

            val messageView = dialog.findViewById(R.id.txt_message_basic_alert) as TextView
            messageView.movementMethod = LinkMovementMethod.getInstance()
            messageView.text = message

            val titleView = dialog.findViewById(R.id.txt_title_basic_alert) as TextView
            if (title?.isNotEmpty() == true) {
                titleView.visibility = View.VISIBLE
            } else {
                titleView.visibility = View.GONE
            }
            titleView.text = title

            val dialogButton: AppCompatButton =
                dialog.findViewById(R.id.btn_confirm_basic_alert) as AppCompatButton
            dialogButton.setOnClickListener {
                onButtonClicked?.invoke()
                dialog.dismiss()
            }
            dialog.show()
        }

        fun showBasicLoadingDialog(
            activity: Activity,
            title: CharSequence,
            cancelable: Boolean,
            onButtonClicked: (() -> Unit)? = null
        ) {
            loadingDialog(activity, title, cancelable, onButtonClicked).show()
        }

        fun loadingDialog(
            activity: Activity,
            title: CharSequence,
            cancelable: Boolean,
            onButtonClicked: (() -> Unit)?
        ): Dialog {
            val dialog = Dialog(activity).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                setContentView(R.layout.view_loading)
            }

            (dialog.findViewById(R.id.view_loading_message) as TextView).text = title
            dialog.setCancelable(cancelable)

            return dialog
        }
    }
}