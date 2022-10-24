package com.stp.maunyucibeta.data

import javax.inject.Inject

class UserRepository @Inject constructor(private val dao: UserDao) {

    val allUserData = dao.getAllUser()

    suspend fun insertUser(user: UserData): Long {
        return dao.insertUser(user)
    }

    suspend fun updateUser(user: UserData): Int {
        return dao.updateUser(user)
    }

    suspend fun delete(user: UserData): Int {
        return dao.deleteUser(user)
    }

    fun deleteAll(): Int {
        return dao.deleteAll()
    }
}