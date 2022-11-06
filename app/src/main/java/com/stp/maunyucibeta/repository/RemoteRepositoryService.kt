package com.stp.maunyucibeta.repository

import com.stp.maunyucibeta.model.BaseResponse
import com.stp.maunyucibeta.model.auth.Login
import com.stp.maunyucibeta.model.layanan.Layanan
import com.stp.maunyucibeta.model.layanan.LayananResponse
import com.stp.maunyucibeta.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RemoteRepositoryService {
    @GET("layanan/get-layanan")
    suspend fun getLayanan(): BaseResponse<List<Layanan>>

    @GET("layanan/get-layanan/{id}/single")
    suspend fun getSingleLayanan(@Path("id") id: String): BaseResponse<List<Layanan>>

    @FormUrlEncoded
    @POST("login/login-process")
    fun login (@FieldMap map: HashMap<String, String>): Call<Login>

    companion object {
        var retrofitService: RemoteRepositoryService? = null

        fun getInstance(): RemoteRepositoryService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RemoteRepositoryService::class.java)
            }
            return retrofitService!!
        }
    }
}