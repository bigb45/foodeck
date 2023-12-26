package com.example.data.api_services

import com.example.data.models.UserData
import com.example.data.repositories.NewUserCredentials
import com.example.data.repositories.UserCredentials
import com.example.data.util.AccessTokenRequest
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun logUserIn(@Body loginRequest: UserCredentials): Response<TokenDto>

    @POST("create_account")
    suspend fun createUser(@Body createUserRequest: NewUserCredentials): Response<TokenDto>



    @POST("token")
    suspend fun getAccessToken(@Body refreshToken: AccessTokenRequest): Response<RefreshTokenDto>
}

data class TokenDto(
    val userId: String,
    val accessToken: String,
    val refreshToken: String
)

// TODO: move this from here
data class RefreshTokenDto(
    @SerializedName("accessToken") val token: String
)
