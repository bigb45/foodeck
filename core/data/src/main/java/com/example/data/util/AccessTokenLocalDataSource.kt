package com.example.data.util

import com.example.data.R
import com.example.data.api_services.AuthApiService
import javax.inject.Inject

class AccessTokenLocalDataSource @Inject constructor(
    private val storage: PreferencesManager,
){
    fun getAccessToken(): String{
        val token =  storage.getToken(R.string.access_token)
        if(token.isNullOrEmpty()){
            throw NotAuthenticatedException()
        }

        return token
    }

    fun getRefreshToken(): String{
        val token =  storage.getToken(R.string.refresh_token)
        if(token.isNullOrEmpty()){
            throw NoRefreshTokenException()
        }

        return token
    }

    fun writeAccessToken(token: String){
        if(token.isEmpty()){
            return
        }
        storage.writeToken(R.string.access_token, token)
    }

    fun writeRefreshToken(token: String){
        if(token.isEmpty()){
            return
        }
        storage.writeToken(R.string.refresh_token, token)
    }
}

class NotAuthenticatedException: Exception()
class NoRefreshTokenException: Exception()