package com.stp.maunyucibeta.repository

import com.stp.maunyucibeta.model.BaseResponse
import com.stp.maunyucibeta.model.auth.Login
import com.stp.maunyucibeta.model.layanan.Layanan
import com.stp.maunyucibeta.model.layanan.LayananResponse
import retrofit2.Call
import javax.inject.Inject

class RemoteRepositoryDaoImpl @Inject constructor(private val remoteRepositoryService: RemoteRepositoryService): RemoteRepositoryDao {

    override suspend fun getLayanan(): BaseResponse<List<Layanan>> {
        return remoteRepositoryService.getLayanan()
    }

    override suspend fun getSingleLayanan(id: String): BaseResponse<List<Layanan>> {
        return remoteRepositoryService.getSingleLayanan(id)
    }

    override fun login(map: HashMap<String, String>): Call<Login> {
        return remoteRepositoryService.login(map)
    }
}