package com.example.data.repositories


import com.example.data.models.SignInAuthResponseModel
import com.example.data.models.SignupAuthResponseModel
import com.example.data.models.TokenAuthResponseModel
import com.example.data.models.UserDetails
import com.example.data.models.UserSignInInfo
import com.example.data.models.UserSignUpInfo
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun createUser(user: UserSignUpInfo): Flow<SignupAuthResponseModel>
    suspend fun signUserIn(user: UserSignInInfo): Flow<SignInAuthResponseModel>
    suspend fun signUserOut(): Flow<Boolean>
    suspend fun getUsernameFromEmail(email: String): String
    suspend fun getUserById(id: String): UserDetails
    suspend fun addUserInformation(userData: UserDetails)
    suspend fun authenticateUserWithToken(token: String, provider: String): Flow<TokenAuthResponseModel>
    suspend fun checkDuplicatePhoneNumber(phoneNumber: String): Boolean
}