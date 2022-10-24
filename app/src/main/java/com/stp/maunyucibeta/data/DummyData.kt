package com.stp.maunyucibeta.data

import com.stp.maunyucibeta.R
import com.stp.maunyucibeta.model.DummyLayanan
import com.stp.maunyucibeta.model.beranda.Menu

object DummyData {

    private val dummy_nama_layanan = arrayOf(
        "Kiloan",
        "Kiloan",
        "Satuan",
        "Selimut",
        "Boneka"
    )

    private val dummy_opsi_layanan = arrayOf(
        "Cuci >> Kering >> Setrika",
        "Cuci >> Kering >> Setrika",
        "Cuci >> Kering",
        "Cuci >> Kering",
        "Cuci"
    )

    private val dummy_image_layanan = arrayOf(
        R.drawable.g_kiloan,
        R.drawable.g_kiloan,
        R.drawable.g_kemeja,
        R.drawable.g_selimut,
        R.drawable.g_boneka,
    )

    private val dummy_jenis_layanan = arrayOf(
        "Regular",
        "Regular",
        "Regular",
        "Express",
        "Express",
    )

    private val dummy_harga_layanan = arrayOf(
        "Rp 5.000/kg - 3 Hari",
        "Rp 10.000/kg - 1 Hari",
        "Rp 5.000/kg - 3 Hari",
        "Rp 10.000/kg - 1 Hari",
        "Rp 10.000/kg - 1 Hari",
    )

    val listDummyLayanan: ArrayList<DummyLayanan>
        get() {
            val listLayanan = ArrayList<DummyLayanan>()
            for (i in dummy_image_layanan.indices) {
                val dataLayanan = DummyLayanan()

                dataLayanan.nama_layanan = dummy_nama_layanan[i]
                dataLayanan.opsi_layanan = dummy_opsi_layanan[i]
                dataLayanan.image_layanan = dummy_image_layanan[i]
                dataLayanan.jenis_layanan = dummy_jenis_layanan[i]
                dataLayanan.harga_layanan = dummy_harga_layanan[i]

                listLayanan.add(dataLayanan)
            }

            return listLayanan
        }

    private val dummy_nama_pelanggan = arrayOf(
        "Courtney Henry",
        "Jacob Jones",
        "Ronald Richards",
        "Guy Hawkins",
        "Jane Cooper",
        "Robert Fox"
    )

    private val dummy_phone_pelanggan = arrayOf(
        "(406) 555-0120",
        "(406) 555-0120",
        "(406) 555-0120",
        "(406) 555-0120",
        "(406) 555-0120",
        "(406) 555-0120"
    )

    private val dummy_menu_beranda = arrayOf(
        2000000,
        50,
        100,
        60
    )

    val listDummyMenuBeranda: ArrayList<Menu>
        get() {
            val listMenu = ArrayList<Menu>()
            for (i in dummy_menu_beranda.indices) {
                val dataBeranda = Menu()

                dataBeranda.omset = dummy_menu_beranda[i].toDouble()
                dataBeranda.masuk = dummy_menu_beranda[i]
                dataBeranda.ambil = dummy_menu_beranda[i]
                dataBeranda.selesai = dummy_menu_beranda[i]

                listMenu.add(dataBeranda)
            }

            return listMenu
        }
}