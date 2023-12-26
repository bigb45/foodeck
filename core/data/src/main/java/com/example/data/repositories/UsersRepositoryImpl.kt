package com.example.data.repositories

import android.util.Log.d
import com.example.data.R
import com.example.data.api_services.UserApiService
import com.example.data.models.UserData
import com.example.data.util.AccessTokenRemoteDataSource
import com.example.data.util.PreferencesManager
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val usersService: UserApiService, private val sharedPrefs: PreferencesManager) : UsersRepository {
    override suspend fun getUser(userId: String): UserData {

        val token = "Bearer ${sharedPrefs.getToken(R.string.access_token)}"
        d("token", token)
        return try {
            val user = usersService.getUser(token, userId)
            d("error", user.code().toString())
            d("error", userId)
            when{
                user.isSuccessful -> {
                    d("error", user.body()?.name.toString())
                    with(user.body()!!){
                        UserData(this.userId, this.name, this.email)
                    }
                }
                else -> {
                    d("error", user.body().toString())
                    UserData(null)
                }
            }

        } catch (e: Exception){
            d("error", e.message.toString())
            return UserData(null)
        }

    }
}