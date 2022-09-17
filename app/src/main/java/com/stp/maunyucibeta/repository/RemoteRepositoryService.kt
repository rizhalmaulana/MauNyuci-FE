package com.stp.maunyucibeta.repository

import com.stp.maunyucibeta.model.BaseResponse
import com.stp.maunyucibeta.model.Layanan
import com.stp.maunyucibeta.helper.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteRepositoryService {

    @GET("get-layanan")
    suspend fun getLayanan(): BaseResponse<List<Layanan>>

    @GET("get-layanan/{id}/single")
    suspend fun getSingleLayanan(@Path("id") id: String): BaseResponse<Layanan>

    companion object {
        var remoteRepositoryService: RemoteRepositoryService? = null

        fun getInstance() : RemoteRepositoryService {
            if (remoteRepositoryService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constant.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                remoteRepositoryService = retrofit.create(RemoteRepositoryService::class.java)
            }
            return remoteRepositoryService!!
        }
    }
}