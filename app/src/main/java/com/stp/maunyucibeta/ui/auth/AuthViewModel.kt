package com.stp.maunyucibeta.ui.auth

import android.annotation.SuppressLint
import android.text.Selection
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputEditText
import com.stp.maunyucibeta.base.BaseViewModel
import com.stp.maunyucibeta.data.UserData
import com.stp.maunyucibeta.data.UserRepository
import com.stp.maunyucibeta.extension.afterTextChanged
import com.stp.maunyucibeta.helper.Event
import com.stp.maunyucibeta.helper.Static
import com.stp.maunyucibeta.model.auth.Login
import com.stp.maunyucibeta.repository.RemoteRepository
import com.stp.maunyucibeta.repository.ResourceRepository
import com.stp.maunyucibeta.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val userRepository: UserRepository,
    private val resourceRepository: ResourceRepository,
    private val preferenceManager: PreferenceManager,
) : BaseViewModel() {

    private var validEmailorHp: Boolean = false
    private var validPasswordFormat: Boolean = false
    private var onChangedDisabled: Boolean = false

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(true)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading

    private val _emailValue = MutableLiveData<String>()
    private val emailValue: MutableLiveData<String>
        get() = _emailValue

    private val _hideErrorEmailFormat = MutableLiveData<Boolean>()
    val hideErrorEmailFormat: LiveData<Boolean>
        get() = _hideErrorEmailFormat

    private val _passwordValue = MutableLiveData<String>()
    private val passwordValue: MutableLiveData<String>
        get() = _passwordValue

    private val _hideErrorPasswordFormat = MutableLiveData<Boolean>()
    val hideErrorPasswordFormat: LiveData<Boolean>
        get() = _hideErrorPasswordFormat

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean>
        get() = _isValid

    private val _loginError: MutableLiveData<Exception?> by lazy {
        MutableLiveData<Exception?>(null)
    }

    val loginError: LiveData<Exception?>
        get() = _loginError

    private val _statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _statusMessage

    private val _login: MutableLiveData<UserData?> by lazy {
        MutableLiveData(null)
    }

    val login: MutableLiveData<UserData?>
        get() = _login

    fun resetState() {
        setError(null)
        _loading.value = false
        _login.value = null
        _loginError.value = null
        setError(null)
    }

    fun attachInputFormat(
        inputEmailFormat: TextInputEditText,
        inputPasswordFormat: TextInputEditText
    ) {
        inputEmailFormat.apply {
            afterTextChanged {
                if (!onChangedDisabled) {
                    onChangedDisabled = true
                    _emailValue.value = this
                    setText(emailValue.value)

                    validEmailorHp = Static.isValidEmail(inputEmailFormat.text?.trim().toString())
                    _hideErrorEmailFormat.value = validEmailorHp

                    _isValid.value = validEmailorHp && validPasswordFormat
                    Selection.setSelection(inputEmailFormat.text, length())
                    onChangedDisabled = false
                }
            }
        }

        inputPasswordFormat.apply {
            afterTextChanged {
                if (!onChangedDisabled) {
                    onChangedDisabled = true
                    _passwordValue.value = this
                    setText(passwordValue.value)

                    validPasswordFormat =
                        Static.isValidPassword(inputPasswordFormat.text?.trim().toString())
                    _hideErrorPasswordFormat.value = validPasswordFormat

                    _isValid.value = validEmailorHp && validPasswordFormat
                    Selection.setSelection(inputPasswordFormat.text, length())
                    onChangedDisabled = false
                }
            }
        }
    }

    fun fetchLogin(map: HashMap<String, String>) = viewModelScope.launch {
        _loading.value = true
        try {
            val responseLogin = remoteRepository.login(map)
            responseLogin.enqueue(object : Callback<Login> {
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    val userBody = response.body()
                    val userState = userBody?.state

                    if (userBody != null) {
                        if (userState == true) {
                            viewModelScope.launch {
                                try {

                                    userBody.apply {
                                        val outletId = this.data.outlet.outlet_id
                                        val outletUtama = this.data.outlet.outlet_utama
                                        val createdAt = this.data.outlet.CreatedAt
                                        val outletData = this.data.outlet.outlet_data

                                        val idUser = this.data.user.user_id
                                        val nameUser = this.data.user.name
                                        val emailUser = this.data.user.email

                                        val accessToken = this.token.access_token
                                        val refreshToken = this.token.refresh_token

                                        val resultUserData = UserData(
                                            0,
                                            outletId,
                                            outletUtama,
                                            createdAt,
                                            outletData,
                                            idUser,
                                            nameUser,
                                            emailUser,
                                            accessToken,
                                            refreshToken
                                        )

                                        insert(resultUserData)
                                    }

                                } catch (exception: IllegalAccessException) {
                                    _loading.value = false
                                    _statusMessage.value = Event(response.errorBody().toString())
                                }
                            }

                        } else {
                            _loading.value = false
                            _statusMessage.value = Event(response.errorBody().toString())
                        }
                    } else {
                        _loading.value = false
                        _statusMessage.value = Event(response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    _loading.value = false
                    _statusMessage.value = Event(t.message.toString())
                }
            })
            _loading.value = false

        } catch (exception: Exception) {
            _loading.value = false
            _statusMessage.value = Event("Yah, login gagal nih!! Coba lagi yuk.")
        }
    }

    fun isAnonymous(): Boolean {
        return !preferenceManager.isLoggedIn()
    }

    @SuppressLint("LogNotTimber")
    fun insert(user: UserData) = viewModelScope.launch {
        val rowInsertId: Long = userRepository.insertUser(user)

        if (rowInsertId > -1) {
            _login.value = user

            //Save to preference store
            preferenceManager.saveUser(user)
            preferenceManager.saveLogin(true)

            Log.i("Room", "insert room: user berhasil terdaftar")

            _loading.value = false
        } else {
            _login.value = null
            Log.i("Room", "insert room: user sudah terdaftar atau gagal")

            _loading.value = false
        }
    }
}