package com.stp.maunyucibeta.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stp.maunyucibeta.model.auth.Data
import com.stp.maunyucibeta.model.auth.Token

@Entity(tableName = "userbeta_data_table")
data class UserData(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "outlet_id")
    var outlet_id: String?,

    @ColumnInfo(name = "outlet_utama")
    var outlet_utama: Boolean?,

    @ColumnInfo(name = "CreatedAt")
    var created_at: String?,

    @ColumnInfo(name = "outlet_data")
    var outlet_data: String? = null,

    @ColumnInfo(name = "user_id")
    var user_id: String?,

    @ColumnInfo(name = "user_name")
    var name: String?,

    @ColumnInfo(name = "user_email")
    var email: String?,

    @ColumnInfo(name = "user_access_token")
    val access_token: String?,

    @ColumnInfo(name = "user_refresh_token")
    val refresh_token: String?
)