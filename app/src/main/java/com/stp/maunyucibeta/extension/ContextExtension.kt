package com.stp.maunyucibeta.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import com.stp.maunyucibeta.BuildConfig
import com.stp.maunyucibeta.R

import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import java.io.File
import java.lang.Exception
import timber.log.Timber

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
    val clip = ClipData.newPlainText(getString(R.string.app_name), text)
    clipboard?.setPrimaryClip(clip)
    showToast("Berhasil disalin")
}

fun Context.createImageFile(): File {
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val file = File(storageDir, System.currentTimeMillis().toString() + "_kujang_mobile.jpg")
    if (file.exists()) file.delete()
    return file
}

suspend fun Context.compressFile(origin: File): File {
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val destination = File(storageDir, "compressed_kujang_mobile.jpg")
    if (destination.exists()) destination.delete()
    return Compressor.compress(this, origin) {
        default()
        destination(destination)
    }
}

fun Context.getUriFromFile(file: File): Uri = FileProvider.getUriForFile(
    this,
    BuildConfig.APPLICATION_ID + ".provider",
    file
)

fun Context.getRealPathFromUri(uri: Uri): Uri? {
    return try {
        var contentUri: Uri? = null
        contentResolver.query(uri, null, null, null, null)?.use {
            it.moveToFirst()
            it.getColumnIndex(MediaStore.Images.ImageColumns.DATA).let { index ->
                contentUri = File(it.getString(index)).toUri()
            }
        }
        contentUri
    } catch (exception: Exception) {
        Timber.tag(this::class.java.name).e(exception)
        null
    }
}

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, duration).show()
}

fun Context.showBlackToast(text: String, duration: Int = Toast.LENGTH_LONG) {
    val toast: Toast = Toast.makeText(this, text, duration)
    toast.view?.background = ContextCompat.getDrawable(this, R.drawable.bg_round_black)
    val textMessage = toast.view?.findViewById<View>(android.R.id.message) as TextView
    textMessage.setTextColor(Color.WHITE)
    toast.show()
}

fun Context.fragmentActivity(maxStack: Int = 20): FragmentActivity? {
    var curContext = this
    var maxDepth = maxStack
    while (--maxDepth > 0 && curContext !is FragmentActivity) {
        curContext = (curContext as ContextWrapper).baseContext
    }
    return (curContext as? FragmentActivity)
}