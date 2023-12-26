package com.example.data.api_services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

import retrofit2.http.Path

interface UserApiService {
    @GET("{userId}")
    suspend fun getUser(@Header("Authorization") token: String, @Path("userId") userId: String): Response<UserDto>
}

data class UserDto(
    val userId: String,
    val name: String,
    val email: String,
)