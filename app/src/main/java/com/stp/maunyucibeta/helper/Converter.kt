package com.stp.maunyucibeta.helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stp.maunyucibeta.model.auth.Login

class Converter {
    @TypeConverter
    fun fromSubscriber(actor: Login):String{
        val type = object : TypeToken<Login>() {}.type
        return Gson().toJson(actor,type)
    }

    @TypeConverter
    fun toSubscriber(actorString: String): Login{
        val type = object : TypeToken<Login>() {}.type
        return Gson().fromJson(actorString,type)
    }
}