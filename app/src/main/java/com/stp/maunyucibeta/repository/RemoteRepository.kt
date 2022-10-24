package com.stp.maunyucibeta.repository

import com.stp.maunyucibeta.model.BaseResponse
import com.stp.maunyucibeta.model.auth.Login
import com.stp.maunyucibeta.model.layanan.Layanan
import retrofit2.Call
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val remoteRepositoryDao: RemoteRepositoryDao) : RemoteRepositoryDao {

    override suspend fun getLayanan(): BaseResponse<List<Layanan>> {
        return remoteRepositoryDao.getLayanan()
    }

    override suspend fun getSingleLayanan(id: String): BaseResponse<List<Layanan>> {
        return remoteRepositoryDao.getSingleLayanan(id)
    }

    override fun login(map: HashMap<String, String>): Call<Login> {
        return remoteRepositoryDao.login(map)
    }
}