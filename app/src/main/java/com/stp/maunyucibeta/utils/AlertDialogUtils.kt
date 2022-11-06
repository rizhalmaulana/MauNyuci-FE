package com.stp.maunyucibeta.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.stp.maunyucibeta.R

object AlertDialogUtils {

    var dialog: Dialog? = null
    fun displayLoadingWithText(
        context: Context?,
        text: String?,
        cancelable: Boolean
    ) {
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.view_alert_dialog_loading)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(cancelable)

        val textView = dialog!!.findViewById<TextView>(R.id.text)
        textView.text = text

        try {
            dialog!!.show()
        } catch (e: Exception) { }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) { }
    }
}