package com.example.data.repositories


import com.example.data.models.SignInAuthResponseModel
import com.example.data.models.SignupAuthResponseModel
import com.example.data.models.UserDetailsModel
import com.example.data.models.UserSignInModel
import com.example.data.models.UserSignUpModel
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun createUser(user: UserSignUpModel): Flow<SignupAuthResponseModel>
    suspend fun signUserIn(user: UserSignInModel): Flow<SignInAuthResponseModel>
    suspend fun signUserOut(): Flow<Boolean>
    suspend fun getUsernameFromEmail(email: String): String
    suspend fun getUserById(id: String): UserDetailsModel
    suspend fun addUserInformation(userData: UserDetailsModel)
    suspend fun authenticateUserWithToken(token: String, provider: String)
    suspend fun checkDuplicatePhoneNumber(phoneNumber: String): Boolean
}