package com.stp.maunyucibeta.repository

import com.stp.maunyucibeta.model.BaseResponse
import com.stp.maunyucibeta.model.auth.Login
import com.stp.maunyucibeta.model.layanan.Layanan
import retrofit2.Call

interface RemoteRepositoryDao {

    suspend fun getLayanan(): BaseResponse<List<Layanan>>
    suspend fun getSingleLayanan(id: String): BaseResponse<List<Layanan>>
    fun login(map: HashMap<String, String>): Call<Login>
}