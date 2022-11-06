package com.stp.maunyucibeta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter<T, B : ViewDataBinding>(
    var list: List<T>,
    private val layout_id: Int,
    val onBind: (B, T) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter<T, B>.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(parent.context),
            layout_id, parent, false
        )
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        onBind(holder.binding, list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newMenuList: List<T>) {
        list = newMenuList
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(internal var binding: B) : RecyclerView.ViewHolder(binding.root)

}
