package com.stp.maunyucibeta.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.model.layanan.SubLayanan

class RecyclerViewSubLayanan(private var listSubLayanan: List<SubLayanan>) :
    RecyclerView.Adapter<RecyclerViewSubLayanan.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewSubLayanan.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_item_sub_layanan, parent, false))

    override fun onBindViewHolder(holder: RecyclerViewSubLayanan.ViewHolder, position: Int) {
        holder.bind(listSubLayanan[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = listSubLayanan.size

    inner class ViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
        @SuppressLint("SetTextI18n", "LogNotTimber")
        fun bind(subLayanan: SubLayanan) {

            Log.i("recycle sub layanan", "bind: ${subLayanan.nama_sub_layanan}")

            itemView.findViewById<TextView>(R.id.txt_item_jenis_layanan).text =
                subLayanan.nama_sub_layanan

            if (subLayanan.foto_sub_layanan == "") {
                Glide.with(itemView.context)
                    .load(R.drawable.g_kiloan)
                    .override(50, 50)
                    .into(itemView.findViewById(R.id.iv_item_gambar_layanan))
            } else {
                Glide.with(itemView.context)
                    .load(subLayanan.foto_sub_layanan)
                    .override(50, 50)
                    .into(itemView.findViewById(R.id.iv_item_gambar_layanan))
            }

            val estimasiWaktu =
                if (subLayanan.estimasi_waktu == 0) {
                    0
                } else {
                    subLayanan.estimasi_waktu
                }

            val estimasiSubLayanan =
                if (subLayanan.estimasi_sub_layanan == "") {
                    ""
                } else {
                    subLayanan.estimasi_sub_layanan
                }

            val hargaLayanan =
                if (subLayanan.harga_sub_layanan == 0) {
                    0
                } else {
                    subLayanan.harga_sub_layanan
                }

            val satuanLayanan =
                if (subLayanan.satuan_sub_layanan == "") {
                    "kg"
                } else {
                    subLayanan.satuan_sub_layanan
                }

            itemView.findViewById<TextView>(R.id.txt_item_harga_layanan).text =
                "Rp. $hargaLayanan/$satuanLayanan - $estimasiWaktu $estimasiSubLayanan"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSubLayanan(newSubList: List<SubLayanan>) {
        listSubLayanan = newSubList
        notifyDataSetChanged()
    }
}