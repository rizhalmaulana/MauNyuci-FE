package com.stp.maunyucibeta.extension

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.utils.Constants

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = currentFocus
    if (view == null) view = View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.goToHelpPage(message: String = getString(R.string.label_message_help_default)) {
    val url = Constants.WHATS_APP_URL + message
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun Activity.shareSocialMedia(message: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = Constants.TEXT_PLAIN
    intent.putExtra(Intent.EXTRA_SUBJECT, message)
    intent.putExtra(Intent.EXTRA_TEXT, message)
    startActivity(Intent.createChooser(intent, getString(R.string.bagikan_dengan)))
}

fun Activity.shareWhatsapp(message: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = Constants.TEXT_PLAIN
    intent.`package` = Constants.COM_WHATSAPP
    intent.putExtra(Intent.EXTRA_SUBJECT, message)
    intent.putExtra(Intent.EXTRA_TEXT, message)
    try {
        startActivity(intent)
    }catch (e: Exception){
        showToast(getString(R.string.label_whatsapp_not_installed))
    }
}

fun Activity.isKeyboardOpened(): Boolean {
    val rect = Rect()
    val activityRoot = getActivityRoot()
    val visibleThreshold = dip(100)
    activityRoot.getWindowVisibleDisplayFrame(rect)
    val heightDiff = activityRoot.rootView.height - rect.height()
    return heightDiff > visibleThreshold
}

fun Activity.getActivityRoot(): View = (findViewById<ViewGroup>(android.R.id.content)).rootView

fun dip(value: Int): Int = (value * Resources.getSystem().displayMetrics.density).toInt()