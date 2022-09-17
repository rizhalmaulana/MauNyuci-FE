package com.stp.maunyucibeta.repository

import com.stp.maunyucibeta.model.BaseResponse
import com.stp.maunyucibeta.model.Layanan
import javax.inject.Inject

class RemoteRepositoryDaoImpl @Inject constructor(private val remoteRepositoryService: RemoteRepositoryService): RemoteRepositoryDao {

    override suspend fun getLayanan(): BaseResponse<List<Layanan>> {
        return remoteRepositoryService.getLayanan()
    }

    override suspend fun getSingleLayanan(id: String): BaseResponse<Layanan> {
        return remoteRepositoryService.getSingleLayanan(id)
    }
}