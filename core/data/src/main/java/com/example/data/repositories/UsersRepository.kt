package com.example.data.repositories

import com.example.data.models.UserDetailsModel

interface UsersRepository {
    suspend fun getUser(userId: String): UserDetailsModel
}