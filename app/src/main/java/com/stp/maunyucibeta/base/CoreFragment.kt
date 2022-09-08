package com.stp.maunyucibeta.base

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog

abstract class CoreFragment<binding : ViewDataBinding> : Fragment(), BaseView.Fragment<binding> {

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    open lateinit var binding: binding
//    protected open fun getSecurityFlag(): Boolean = !BuildConfig.DEBUG
    protected open fun getSecurityFlag(): Boolean = false
    protected open val listenBackPressed = false
    private val callback by lazy {
        object : OnBackPressedCallback(listenBackPressed) {
            override fun handleOnBackPressed() = onBackPressed()
        }
    }
    lateinit var materialDialog: MaterialDialog
    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflateConfigurator(inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        materialDialog = MaterialDialog(requireContext())
            .message(null, "Loading...")
            .cancelable(false);
    }

    private fun inflateConfigurator(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            this.initializeView()
        }.root
    }

    fun <T> LiveData<T>.observe(function: T.() -> Unit) {
        this.observe(this@CoreFragment, Observer {
            function.invoke(it)
        })
    }

    fun Window.getSoftInputMode(): Int = attributes.softInputMode

    fun showProgressDialog() {
        materialDialog.show()
    }

    fun showProgressDialog(s: String) {
        materialDialog.apply {
            title(null, s)
            show()
        }
    }

    fun dismissProgressDialog() {
        materialDialog.dismiss()
    }

    fun showMessage(message: String?){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        requireActivity().window?.apply {
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
}