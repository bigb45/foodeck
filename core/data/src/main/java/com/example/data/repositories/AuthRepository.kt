package com.example.data.repositories


import com.example.data.models.AuthResult
import com.example.data.models.NewUserData
import com.example.data.models.UserInfo
import com.google.firebase.auth.FirebaseUser


interface AuthRepository {
    suspend fun createUser(user: NewUserData): AuthResult
    suspend fun signUserIn(user: UserInfo): AuthResult
    suspend fun signUserOut(): AuthResult
    suspend fun getUsernameFromEmail(email: String): String
    suspend fun getUser(id: String): FirebaseUser?
}