package com.stp.maunyucibeta.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stp.maunyucibeta.helper.Converter

@Database(entities = [UserData::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MauNyuciDatabase: RoomDatabase() {
    abstract fun getUserDao() : UserDao
}