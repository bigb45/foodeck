package com.example.data.repositories

import com.example.data.models.UserDetails

interface UsersRepository {
    suspend fun getUser(userId: String): UserDetails
}