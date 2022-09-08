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
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.stp.maunyucibeta.R
import okhttp3.internal.notify

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
//            val dialog = Dialog(activity)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog.setCancelable(false)
//            dialog.setContentView(R.layout.basic_alert_dialog)
//            val iconView = dialog.findViewById<ImageView>(R.id.basic_alert_dialog_icon)
//            if (icon != null) {
//                iconView.setImageDrawable(icon)
//            } else {
//                iconView.visibility = View.GONE
//            }
//            val messageView = dialog.findViewById(R.id.basic_alert_dialog_text) as TextView
//            messageView.movementMethod = LinkMovementMethod.getInstance()
//            messageView.text = message
//            val titleView = dialog.findViewById(R.id.basic_alert_dialog_title) as TextView
//            if (title?.isNotEmpty() == true) {
//                titleView.visibility = View.VISIBLE
//            } else {
//                titleView.visibility = View.GONE
//            }
//            titleView.text = title
//            val noteView = dialog.findViewById(R.id.basic_alert_dialog_note) as TextView
//            if (note?.isNotEmpty() == true) {
//                noteView.visibility = View.VISIBLE
//            } else {
//                noteView.visibility = View.GONE
//            }
//            noteView.text = note
//            val dialogButton: AppCompatButton =
//                dialog.findViewById(R.id.basic_alert_dialog_button_ok) as AppCompatButton
//            dialogButton.setOnClickListener {
//                onButtonClicked?.invoke()
//                dialog.dismiss()
        }
//            dialog.show()
    }

//        fun showBasicAlertConfirmationDialog(
//            activity: Activity,
//            icon: Drawable?,
//            message: CharSequence,
//            title: CharSequence? = "",
//            note: CharSequence? = "",
//            onButtonClicked: (() -> Unit)? = null
//        ) {
//            val dialog = Dialog(activity)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog.setCancelable(false)
//            dialog.setContentView(R.layout.basic_alert_confirmation_dialog)
//            val iconView = dialog.findViewById<ImageView>(R.id.basic_alert_dialog_icon)
//            if (icon != null) {
//                iconView.setImageDrawable(icon)
//            } else {
//                iconView.visibility = View.GONE
//            }
//            val messageView = dialog.findViewById(R.id.basic_alert_dialog_text) as TextView
//            messageView.movementMethod = LinkMovementMethod.getInstance()
//            messageView.text = message
//            val titleView = dialog.findViewById(R.id.basic_alert_dialog_title) as TextView
//            if (title?.isNotEmpty() == true) {
//                titleView.visibility = View.VISIBLE
//            } else {
//                titleView.visibility = View.GONE
//            }
//            titleView.text = title
//            val noteView = dialog.findViewById(R.id.basic_alert_dialog_note) as TextView
//            if (note?.isNotEmpty() == true) {
//                noteView.visibility = View.VISIBLE
//            } else {
//                noteView.visibility = View.GONE
//            }
//            noteView.text = note
//            val dialogButton: AppCompatButton =
//                dialog.findViewById(R.id.basic_alert_dialog_button_ok) as AppCompatButton
//            dialogButton.setOnClickListener {
//                onButtonClicked?.invoke()
//                dialog.dismiss()
//            }
//
//            val dialogButtonNo: AppCompatButton =
//                dialog.findViewById(R.id.basic_alert_dialog_button_no) as AppCompatButton
//            dialogButtonNo.setOnClickListener {
//                dialog.dismiss()
//            }
//            dialog.show()
//        }

    fun showBasicLoadingDialog(
        activity: Activity,
        message: CharSequence,
        title: CharSequence,
        cancelable: Boolean,
        onButtonClicked: (() -> Unit)? = null
    ) {
        loadingDialog(activity, message, title, cancelable, onButtonClicked).show()
    }

    fun loadingDialog(
        activity: Activity,
        message: CharSequence,
        title: CharSequence,
        cancelable: Boolean,
        onButtonClicked: (() -> Unit)?
    ): Dialog {
        val dialog = Dialog(activity).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
//                setContentView(R.layout.dialog_loading)
        }
//            (dialog.findViewById(R.id.basic_loading_dialog_text) as TextView).apply {
//                movementMethod = LinkMovementMethod.getInstance()
//                text = message
//            }
//            (dialog.findViewById(R.id.basic_loading_dialog_title) as TextView).text = title
//            if (cancelable) {
//                (dialog.findViewById(R.id.basic_loading_close_button) as AppCompatImageView).setOnClickListener {
//                    onButtonClicked?.invoke()
//                    if (cancelable) {
//                        dialog.dismiss()
//                    }
//                }
//            } else {
//                (dialog.findViewById(R.id.basic_loading_close_button) as AppCompatImageView).visibility =
//                    View.GONE
//            }
        return dialog
    }

//        fun showDialogOptionBottomSheet(
//            activity: Activity,
//            description: String,
//            yesTitle: String?,
//            noTitle: String?,
//            yesButtonClicked: (() -> Unit)? = null,
//            noButtonClicked: (() -> Unit)? = null
//        ) {
//            val dialog = MaterialDialog(activity, BottomSheet(LayoutMode.WRAP_CONTENT))
//                .customView(R.layout.dialog_confirmation, scrollable = true)
//                .cornerRadius(activity.resources.getDimension(R.dimen.card_corner_radius_normal))
//
//            val descriptionText =
//                dialog.findViewById<AppCompatTextView>(R.id.dialog_finish_quiz_info)
//            descriptionText.text = description
//            val btnYes = dialog.findViewById<AppCompatButton>(R.id.dialog_finish_quiz_button_yes)
//            val btnNo = dialog.findViewById<AppCompatButton>(R.id.dialog_finish_quiz_button_no)
//            btnYes.text = yesTitle
//            btnYes.setOnClickListener {
//                dialog.dismiss()
//                yesButtonClicked?.invoke()
//            }
//            btnNo.text = noTitle
//            btnNo.setOnClickListener {
//                dialog.dismiss()
//                noButtonClicked?.invoke()
//            }
//            dialog.show()
//        }

//        fun showMessageBottomSheet(
//            activity: Activity,
//            description: String,
//            cancelable: Boolean,
//            yesButtonClicked: (() -> Unit)? = null,
//        ) {
//            val dialog = MaterialDialog(activity, BottomSheet(LayoutMode.WRAP_CONTENT))
//                .customView(R.layout.dialog_confirmation, scrollable = true)
//                .cancelOnTouchOutside(cancelable)
//                .cornerRadius(activity.resources.getDimension(R.dimen.card_corner_radius_normal))
//
//            val descriptionText =
//                dialog.findViewById<AppCompatTextView>(R.id.dialog_finish_quiz_info)
//            descriptionText.text = description
//            val btnYes = dialog.findViewById<AppCompatButton>(R.id.dialog_finish_quiz_button_yes)
//            val btnNo = dialog.findViewById<AppCompatButton>(R.id.dialog_finish_quiz_button_no)
//            btnNo.gone()
//            btnYes.setOnClickListener {
//                dialog.dismiss()
//                yesButtonClicked?.invoke()
//            }
//            dialog.show()
//        }

//        fun showImageDialog(
//            activity: Activity,
//            imageUrl: String?,
//            onButtonClicked: (() -> Unit)? = null
//        ) {
//            val dialog = Dialog(activity)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog.setCancelable(false)
//            dialog.setContentView(R.layout.dialog_show_image)
//            val iconView = dialog.findViewById<AppCompatImageView>(R.id.dialog_show_image)
//            if (imageUrl != null) {
//                Glide.with(activity.applicationContext)
//                    .load(imageUrl)
//                    .into(iconView)
//            } else {
//                iconView.gone()
//            }
//
//            val dialogButton =
//                dialog.findViewById<AppCompatImageView>(R.id.dialog_show_image_finish)
//            dialogButton.setOnClickListener {
//                onButtonClicked?.invoke()
//                dialog.dismiss()
//            }
//            dialog.show()
//        }

//        fun showImageBottomSheet(
//            activity: Activity,
//            imageUrl: String?,
//            onButtonCloseClicked: (() -> Unit)? = null,
//            onImageClicked: (() -> Unit)? = null
//        ) {
//            val dialog = MaterialDialog(activity, BottomSheet(LayoutMode.MATCH_PARENT))
//                .customView(R.layout.dialog_show_image, scrollable = true)
//                .cornerRadius(activity.resources.getDimension(R.dimen.card_corner_radius_large))
//
//            val iconView = dialog.findViewById<AppCompatImageView>(R.id.dialog_show_image)
//            if (imageUrl != null) {
//                Glide.with(activity.applicationContext)
//                    .load(imageUrl)
//                    .into(iconView)
//                iconView.setOnClickListener {
//                    onImageClicked?.invoke()
//                }
//            } else {
//                iconView.gone()
//            }
//
//            val dialogButton =
//                dialog.findViewById<AppCompatImageView>(R.id.dialog_show_image_finish)
//            dialogButton.setOnClickListener {
//                onButtonCloseClicked?.invoke()
//                dialog.dismiss()
//            }
//            dialog.show()
//        }

}