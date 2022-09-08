package com.stp.maunyucibeta.base

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.appbar.AppBarLayout
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.utils.StatusBar


abstract class CoreActivity<binding : ViewDataBinding> : AppCompatActivity(),
    BaseView.Activity<binding> {

    companion object {
        const val PROPERTY_ELEVATION = "elevation"
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    open lateinit var binding: binding
//    protected open fun getSecurityFlag(): Boolean = !BuildConfig.DEBUG
    protected open fun getSecurityFlag(): Boolean = false
    var toolbarTitle: AppCompatTextView? = null
    lateinit var materialDialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = this
        binding = DataBindingUtil.setContentView(activity, getLayoutId())
        binding.apply {
            this.lifecycleOwner = activity
            this.initializeView(savedInstanceState)
        }
        materialDialog = MaterialDialog(this)
            .message(null, "Loading...")
            .cancelable(false);
    }

    protected open fun setupActionBar(
        toolbar: Toolbar,
        toolbarTitle: AppCompatTextView,
        appBarLayout: AppBarLayout
    ) {
        StatusBar.setLightStatusBar(window)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
        this@CoreActivity.toolbarTitle = toolbarTitle
        val stateListAnimator = StateListAnimator()
        stateListAnimator.addState(
            IntArray(0),
            ObjectAnimator.ofFloat(appBarLayout, PROPERTY_ELEVATION, 0F)
        )
        appBarLayout.stateListAnimator = stateListAnimator
        toolbar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_back)
        toolbar.contentInsetStartWithNavigation = 0
    }

    fun <T> LiveData<T>.observe(function: T.() -> Unit) {
        this.observe(this@CoreActivity, Observer {
            function.invoke(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        window?.apply {
            when {
                getSecurityFlag() -> setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
                else -> clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
        super.onResume()
    }

    protected open fun getVersionNameInfo(): String = try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }

    protected open fun getVersionCodeInfo(): String = try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toString()
        } else {
            packageInfo.versionCode.toString()
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }

    fun showMessage(message: String?){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun displayError(message: String?){
        if(message != null){
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    fun showProgressDialog() {
        materialDialog.show()
    }

    fun showProgressDialog(s: String) {
        materialDialog.apply {
            title(null, s)
            show()
        }
    }

    fun dissmissProgressDialog() {
        materialDialog.dismiss()
    }

    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun showKeyboard() {
        val inputMethodManager: InputMethodManager =
            applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    open fun fromHtml(html: String?): Spanned? {
        return when {
            html == null -> {
                SpannableString("")
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
            }
            else -> {
                Html.fromHtml(html)
            }
        }
    }
}