package com.example.data.repositories


import com.example.data.models.AuthResult
import com.example.data.models.UserSignUpModel
import com.example.data.models.UserLoginCredentials
import com.google.firebase.auth.FirebaseUser


interface AuthRepository {
    suspend fun createUser(user: UserSignUpModel): AuthResult
    suspend fun signUserIn(user: UserLoginCredentials): AuthResult
    suspend fun signUserOut(): AuthResult
    suspend fun getUsernameFromEmail(email: String): String
    suspend fun getUser(id: String): FirebaseUser?
}