package com.example.data.repositories

import com.example.data.models.UserData

interface UsersRepository {
    suspend fun getUser(userId: String): UserData
}