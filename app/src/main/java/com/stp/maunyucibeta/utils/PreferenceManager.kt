package com.stp.maunyucibeta.utils

import android.content.Context
import com.google.gson.Gson
import com.stp.maunyucibeta.data.UserData
import com.stp.maunyucibeta.model.auth.Login
import javax.inject.Inject

class PreferenceManager @Inject constructor(val app: Context) {

    private val sharedPreferences by lazy {
        app.getSharedPreferences("com.stp.maunyucibeta", Context.MODE_PRIVATE)
    }


    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String = sharedPreferences.getString(key, Utils.String.EMPTY) ?: Utils.String.EMPTY

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String) = sharedPreferences.getInt(key, 0)

    fun onUserBoarded() {
        saveBoolean(USER_BOARDED, true)
    }

    fun isUserBoarded() = getBoolean(USER_BOARDED)

    fun isLoggedIn() : Boolean = getBoolean(IS_LOGGED_IN)

    fun clear() {
        val requestId = getString(INSTANCE_ID)
        val deviceId = getString(DEVICE_ID)
        sharedPreferences.edit().clear().apply()
        sharedPreferences.edit().putString(INSTANCE_ID, requestId).apply()
        sharedPreferences.edit().putString(DEVICE_ID, deviceId).apply()
        onUserBoarded()
    }

    companion object {
        const val DEVICE_ID = "device_id"
        const val USER_BOARDED = "user_boarded"
        const val IS_LOGGED_IN = "is_logged_in"
        const val ACCESS_TOKEN = "access_token"
        const val LOGIN_TYPE = "login_type"
        const val INSTANCE_ID = "instance_id"
        const val LOCATION_SHARING = "location_sharing"
        const val USER_LOGIN = "USER_LOGIN"
        const val USER_TOKEN = "USER_TOKEN"
        const val LAST_REQUEST = "LAST_REQUEST"
    }


    fun saveUser(user: UserData?){
        saveString(USER_LOGIN, Gson().toJson(user))
    }

    fun saveLogin(boolean: Boolean){
        saveBoolean(IS_LOGGED_IN, boolean)
    }

    fun getUser(): UserData? {
        val json = getString(USER_LOGIN)
        return try {
            Gson().fromJson(json, UserData::class.java)
        }catch (exception: Exception) {
            null
        }
    }

    fun setToken(token: String) = saveString(USER_TOKEN, token)
    fun getToken() = getString(USER_TOKEN)

}