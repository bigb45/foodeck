package com.example.data.api_services

import com.example.data.models.UserData
import com.example.data.repositories.NewUserCredentials
import com.example.data.repositories.UserCredentials
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun logUserIn(@Body loginRequest: UserCredentials): Response<TokenDto>

    @POST("create_account")
    suspend fun createUser(@Body createUserRequest: NewUserCredentials): Response<TokenDto>
}

data class TokenDto(
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)