package com.example.data.util

import android.util.Log.d
import com.example.data.api_services.AuthApiService
import com.example.data.api_services.RefreshTokenDto
import com.example.data.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccessTokenRemoteDataSource @Inject constructor(
    private val apiService: AuthApiService,
    private val storage: PreferencesManager,
) {
    private val refreshToken = storage.getToken(R.string.refresh_token)
    suspend fun getAccessToken(){
        withContext(Dispatchers.Default) {
            refreshToken?.let {
                val accessToken = apiService.getAccessToken(AccessTokenRequest(it))
                when{
                    accessToken.isSuccessful -> {
                        d("error", "access token ${accessToken.body().toString()}")
                        storage.writeToken(R.string.access_token, accessToken.body()?.token?: "No token")
                    }

                    accessToken.code() == 403 ->{
                        d("error", "invalid refresh token")
                    }

                    else -> {}
                }

            }

        }
    }

}

data class AccessTokenRequest(
    val token: String
)