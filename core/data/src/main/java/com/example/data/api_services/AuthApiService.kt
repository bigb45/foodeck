package com.example.data.api_services

import com.example.data.models.AccessTokenDto
import com.example.data.models.RefreshTokenDto
import com.example.data.models.TokenDto
import com.example.data.repositories.NewUserCredentials
import com.example.data.repositories.UserCredentials
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

    @POST("token")
    suspend fun getAccessToken(@Body refreshToken: AccessTokenDto): Response<RefreshTokenDto>
}



