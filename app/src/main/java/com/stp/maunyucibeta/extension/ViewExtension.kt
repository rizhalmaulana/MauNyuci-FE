package com.stp.maunyucibeta.extension

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.mikepenz.fastadapter.IItemAdapter

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.showOrGone(isShow: Boolean) {
    if (isShow) visible() else gone()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showOrGone(content: String?) {
    visibility = if (content.isNullOrBlank()) View.GONE else View.VISIBLE
    (this as? TextView)?.text = content
}

fun View.getAttribute(attrs: AttributeSet?, attributes: IntArray, defStyleAttr: Int): TypedArray {
    return context.theme.obtainStyledAttributes(attrs, attributes, defStyleAttr, 0)
}

fun <Model> IItemAdapter<Model, *>.addNew(items: MutableList<Model>) {
    clear()
    add(items)
}

fun ViewPager2.afterPageChange(afterPageChange: (Int) -> Unit): ViewPager2.OnPageChangeCallback {
    return object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            afterPageChange.invoke(position)
        }
    }.apply {
        registerOnPageChangeCallback(this)
    }
}