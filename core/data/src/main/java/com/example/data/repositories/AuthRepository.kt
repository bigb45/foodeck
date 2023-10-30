package com.example.data.repositories


import com.example.data.data.UserData
import com.example.data.models.AuthResult
import com.example.data.data.UserSignUpModel
import com.example.data.data.UserLoginCredentials


interface AuthRepository {
    suspend fun createUser(user: UserSignUpModel): AuthResult
    suspend fun signUserIn(user: UserLoginCredentials): AuthResult
    suspend fun signUserOut(): AuthResult
    suspend fun getUsernameFromEmail(email: String): String
    suspend fun getUserById(id: String): UserData
    suspend fun addUserInformationToDatabase(userData: UserData)
    suspend fun checkDuplicatePhoneNumber(phoneNumber: String): Boolean
}