package com.example.data.repositories


import com.example.data.data.UserData
import com.example.data.data.UserLoginCredentials
import com.example.data.data.UserSignUpModel
import com.example.data.models.AuthResult
import com.example.data.models.ErrorCode
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {

    override suspend fun createUser(user: UserSignUpModel): AuthResult {

        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()
            AuthResult.Success(
                data = UserData(
                    username = user.username,
//                   TODO: throw exception if user has no ID
                    userId = result.user?.uid ?: "test_id", profilePictureUrl = null
                )
            )
        } catch (e: Exception) {
            if (e is FirebaseAuthUserCollisionException) {
                AuthResult.Error(
                    errorMessage = e.message ?: "Unknown error",
                    errorCode = ErrorCode.DUPLICATE_EMAIL
                )
            } else {
                AuthResult.Error(errorMessage = e.message ?: "Unknown error")

            }
        }
    }

    override suspend fun signUserIn(user: UserLoginCredentials): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(user.email, user.password).await()
            AuthResult.Success(
                data = UserData(
                    username = result.user?.displayName ?: "Unknown user",
                    userId = result.user?.uid ?: "test_id",
                    profilePictureUrl = null
                )
            )
        } catch (e: Exception) {

            if (e is FirebaseNetworkException) {
                AuthResult.Error("A network error has occurred")

            } else {
//                there is no way to check for error type thanks to firebase implementation
                AuthResult.Error("Email or password incorrect")
            }
        }

    }

    override suspend fun signUserOut(): AuthResult {
        val authResult: AuthResult = AuthResult.Error("Unimplemented method")
        return authResult
    }

    override suspend fun getUsernameFromEmail(email: String): String {
        return "Unimplemented method"
    }

    override suspend fun getUser(id: String): FirebaseUser? {
        return auth.currentUser
    }
}