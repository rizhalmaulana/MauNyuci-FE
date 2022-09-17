package com.stp.maunyucibeta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.model.DummyLayanan

class LayananAdapter(private val listLayanan: ArrayList<DummyLayanan>) :
    RecyclerView.Adapter<LayananAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayananAdapter.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_layanan, parent, false)
        )

    override fun onBindViewHolder(holder: LayananAdapter.ViewHolder, position: Int) =
        holder.bind(listLayanan[position])

    override fun getItemCount(): Int = listLayanan.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(layanan: DummyLayanan) {
            Glide.with(itemView.context)
                .load(layanan.image_layanan)
                .override(50, 50)
                .into(itemView.findViewById(R.id.iv_item_gambar_layanan))

            itemView.findViewById<TextView>(R.id.txt_item_nama_layanan).text = layanan.nama_layanan
            itemView.findViewById<TextView>(R.id.txt_item_opsi_layanan).text = layanan.opsi_layanan
            itemView.findViewById<TextView>(R.id.txt_item_jenis_layanan).text =
                layanan.jenis_layanan
            itemView.findViewById<TextView>(R.id.txt_item_harga_layanan).text =
                layanan.harga_layanan
        }
    }
}