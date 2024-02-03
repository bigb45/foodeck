package com.example.data.repositories

import android.util.Log.d
import com.example.data.api_services.UserApiService
import com.example.data.models.UserDetails
import com.example.data.util.AccessTokenLocalDataSource
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val usersService: UserApiService, private val tokenDataSource: AccessTokenLocalDataSource) : UsersRepository {
//    TODO: make this function return a flow and handle error states
    override suspend fun getUser(userId: String): UserDetails {

        val token = "Bearer ${tokenDataSource.getAccessToken()}"
        return try {

            val user = usersService.getUser(token, userId)
            d("error", "${user.code()}, $token")

                when {
                    user.isSuccessful -> {
                        user.body()!!
                    }
//                    TODO: handle the case of error. make this return a flow maybe?

                    else -> {
                        UserDetails(null)
                    }
                }


        } catch (e: Exception){
            d("error", "${e.message}")
            return UserDetails(null)
        }

    }
}