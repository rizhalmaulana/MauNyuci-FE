package com.stp.maunyucibeta.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserData) : Long

    @Update
    suspend fun updateUser(user: UserData) : Int

    @Delete
    suspend fun deleteUser(user: UserData) : Int

    @Query("DELETE FROM userbeta_data_table")
    fun deleteAll() : Int

    @Query("SELECT * FROM userbeta_data_table")
    fun getAllUser(): LiveData<UserData>
}