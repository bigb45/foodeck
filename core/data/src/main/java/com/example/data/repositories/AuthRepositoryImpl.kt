package com.example.data.repositories


import com.example.data.models.AuthResult
import com.example.data.models.ErrorCode
import com.example.data.models.NewUserData
import com.example.data.models.UserData
import com.example.data.models.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {

    override suspend fun createUser(user: NewUserData): AuthResult {

        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()

            AuthResult.Success(
                data = UserData(
                    username = user.username,
//                            TODO: throw exception if user has no ID
                    userId = result.user?.uid ?: "test_id",
                    profilePictureUrl = null
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

    override suspend fun signUserIn(user: UserInfo): AuthResult {
        var authResult: AuthResult = AuthResult.Loading
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authResult = AuthResult.Success(
                        data = UserData(
                            username = task.result.user?.displayName ?: "Unknown user",
//                            TODO: throw exception if user has no ID
                            userId = task.result.user?.uid ?: "test_id",
                            profilePictureUrl = null
                        )
                    )
                } else {
                    authResult = AuthResult.Error(task.exception?.message ?: "Unknown error")
                }
            }
        return authResult
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