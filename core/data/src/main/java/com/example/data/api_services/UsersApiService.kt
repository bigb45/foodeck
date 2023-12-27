package com.example.data.api_services

import com.example.data.models.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserApiService {
    @GET("{userId}")
    suspend fun getUser(@Header("Authorization") token: String, @Path("userId") userId: String): Response<UserDto>
}

