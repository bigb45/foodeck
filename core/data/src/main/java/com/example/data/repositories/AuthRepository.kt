package com.example.data.repositories


import com.example.data.data.UserData
import com.example.data.data.UserLoginCredentials
import com.example.data.data.UserSignUpModel
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun createUser(user: UserSignUpModel): Flow<UserData>
    fun signUserIn(user: UserLoginCredentials): Flow<UserData>
    suspend fun signUserOut(): Flow<Boolean>
    suspend fun getUsernameFromEmail(email: String): String
    suspend fun getUserById(id: String): UserData
    suspend fun addUserInformationToDatabase(userData: UserData)
    suspend fun checkDuplicatePhoneNumber(phoneNumber: String): Boolean
}