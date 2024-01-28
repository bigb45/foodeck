package com.example.data.api_services

import com.example.data.models.TokenDto
import com.example.data.models.RefreshToken
import com.example.data.models.AuthenticationResponseDto
import com.example.data.repositories.NewUserCredentials
import com.example.data.repositories.UserCredentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun logUserIn(@Body loginRequest: UserCredentials): Response<AuthenticationResponseDto>

    @POST("create_account")
    suspend fun createUser(@Body createUserRequest: NewUserCredentials): Response<AuthenticationResponseDto>

    @POST("token")
    suspend fun getAccessToken(@Body refreshToken: TokenDto): Response<RefreshToken>

    @POST("login_with_token/{provider}")
    suspend fun authenticateWithGoogleToken(@Path("provider") provider: String, @Body providerTokenDto: TokenDto): Response<AuthenticationResponseDto>
}



