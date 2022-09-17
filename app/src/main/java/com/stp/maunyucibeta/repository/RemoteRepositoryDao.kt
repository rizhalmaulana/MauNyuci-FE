package com.stp.maunyucibeta.repository

import com.stp.maunyucibeta.model.BaseResponse
import com.stp.maunyucibeta.model.Layanan

interface RemoteRepositoryDao {
    suspend fun getLayanan(): BaseResponse<List<Layanan>>
    suspend fun getSingleLayanan(id: String): BaseResponse<Layanan>
}