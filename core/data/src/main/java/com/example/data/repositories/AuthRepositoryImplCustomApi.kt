package com.example.data.repositories

import android.util.Log.d
import com.example.data.api_services.AuthApiService
import com.example.data.models.InternalServerException
import com.example.data.models.InvalidCredentialsException
import com.example.data.models.SignInAuthResponseModel
import com.example.data.models.SignupAuthResponseModel
import com.example.data.models.TokenAuthResponseModel
import com.example.data.models.TokenDto
import com.example.data.models.UnknownException
import com.example.data.models.UserDetailsModel
import com.example.data.models.UserNotFoundException
import com.example.data.models.UserSignInModel
import com.example.data.models.UserSignUpModel
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImplCustomApi @Inject constructor(private val authService: AuthApiService) :
    AuthRepository {
    override fun createUser(user: UserSignUpModel): Flow<SignupAuthResponseModel> {
        val credentials =
            NewUserCredentials(user.username, user.email, user.password)
        return try {
            flow {
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
            d("error", e.message.toString())
            flow { SignupAuthResponseModel.InternalServerError }
        }
    }

    override suspend fun signUserIn(user: UserSignInModel): Flow<SignInAuthResponseModel> {
        val userCredentials = UserCredentials(email = user.email, password = user.password)
        return try {
            flow {
                val res = authService.logUserIn(userCredentials)
                d("tag", res.code().toString())

                emit(
                    when {
                        res.isSuccessful -> {
                            SignInAuthResponseModel.SignInSuccess(res.body()!!)
                        }

                        res.code() == 403 -> {
                            SignInAuthResponseModel.InvalidCredentials
                            throw (InvalidCredentialsException("Invalid credentials"))
                        }

                        res.code() == 404 -> {
                            SignInAuthResponseModel.UserNotFound
                            throw (UserNotFoundException("User does not exist"))
                        }

                        res.code() == 500 -> {
                            SignInAuthResponseModel.InternalServerError
                            throw (InternalServerException("Internal server error"))
                        }

                        else -> {
                            SignInAuthResponseModel.SignInFailure(res.code())
                            throw (UnknownException("Unknown error"))
                        }
                    }
                )
            }

        } catch (e: Exception) {
            d("error", e.message.toString())
            flow { throw (UnknownError("exception caught")) }
        }
    }

    override suspend fun authenticateUserWithToken(
        token: String,
        provider: String,
    ): Flow<TokenAuthResponseModel> {
        return try {
            val res = authService.authenticateWithGoogleToken(provider, TokenDto(token))
            flow {
                emit(
                    when {
                        res.isSuccessful -> {
                            TokenAuthResponseModel.SignInSuccess(res.body()!!)
                        }
                        res.code() == 500 -> {
                            TokenAuthResponseModel.InternalServerError
                            throw (InternalServerException("Error while inserting data"))
                        }

//                        response.code() ==  -> {
//                            d("error", res.message())
//                            TokenAuthResponseModel.SignInFailure(res.code())
//                        }
                        else -> {
                            TokenAuthResponseModel.SignInFailure(res.code())
                            throw (UnknownException("Unknown error"))
                        }
                    }
                )
            }
        } catch (e: Exception) {
            d("error", e.message.toString())
            return flow {
                TokenAuthResponseModel.UnknownError
                throw(UnknownException("Unknown error: ${e.message.toString()}"))
            }
        }
    }

    override suspend fun signUserOut(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getUsernameFromEmail(email: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): UserDetailsModel {
        TODO("Not yet implemented")
    }

    override suspend fun addUserInformation(userData: UserDetailsModel) {
//        TODO: get validation token from google, send it to api and handle request in backend
        d("error", userData.toString())
    }


    override suspend fun checkDuplicatePhoneNumber(phoneNumber: String): Boolean {
        TODO("Not yet implemented")
    }
}

// TODO: move these out of here into data/models folder
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