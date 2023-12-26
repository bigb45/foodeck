package com.example.data.repositories

import android.util.Log
import com.example.data.api_services.AuthApiService
import com.example.data.models.LoginAuthResponseModel
import com.example.data.models.SignupAuthResponseModel
import com.example.data.models.UserData
import com.example.data.models.UserLoginCredentials
import com.example.data.models.UserSignUpModel
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImplCustomApi @Inject constructor(private val authService: AuthApiService) :
    AuthRepository {
    override fun createUser(user: UserSignUpModel): Flow<SignupAuthResponseModel> {
        val credentials =
            NewUserCredentials(user.username, user.email, user.password, )
        return try {
            flow {
                emit(SignupAuthResponseModel.Loading)
                val res = authService.createUser(credentials)

                emit(
                    when {
                        res.isSuccessful -> {

                            SignupAuthResponseModel.SignupSuccess(res.body()!!)
                        }

                        res.code() == 403 -> {
                            SignupAuthResponseModel.UserAlreadyExists
                        }

                        else -> {
                            SignupAuthResponseModel.SignupFailure(res.code())
                        }
                    }
                )

            }
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
            flow { SignupAuthResponseModel.InternalServerError }
        }
    }

    override suspend fun signUserIn(user: UserLoginCredentials): Flow<LoginAuthResponseModel> {
        val userCredentials = UserCredentials(email = user.email, password = user.password)
        return try {
            flow {
                emit(LoginAuthResponseModel.Loading)
                val res = authService.logUserIn(userCredentials)
                Log.d("tag", res.code().toString())

                emit(
                    when {
                        res.isSuccessful -> {
                            LoginAuthResponseModel.LoginSuccess(res.body()!!)
                        }

                        res.code() == 403 -> {
                            LoginAuthResponseModel.InvalidCredentials
                        }

                        res.code() == 404 -> {
                            LoginAuthResponseModel.UserNotFound
                        }

                        else -> {
                            LoginAuthResponseModel.LoginFailure(res.code())
                        }
                    }
                )
            }

        } catch (e: Exception) {
            Log.d("error", e.message.toString())
            flow { LoginAuthResponseModel.Loading }
        }
    }

    override suspend fun signUserOut(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUsernameFromEmail(email: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): UserData {
        TODO("Not yet implemented")
    }

    override suspend fun addUserInformationToDatabase(userData: UserData) {
        TODO("Not yet implemented")
    }

    override suspend fun checkDuplicatePhoneNumber(phoneNumber: String): Boolean {
        TODO("Not yet implemented")
    }
}

data class UserCredentials(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)

data class NewUserCredentials(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
//    @SerializedName("phone_number") val phoneNumber: String,
)