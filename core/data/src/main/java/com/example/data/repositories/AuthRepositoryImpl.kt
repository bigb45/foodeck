package com.example.data.repositories


import android.util.Log
import com.example.data.data.UserData
import com.example.data.data.UserLoginCredentials
import com.example.data.data.UserSignUpModel
import com.example.data.models.AuthResult
import com.example.data.models.ErrorCode
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume


class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    private val db = Firebase.database.reference

    override suspend fun createUser(user: UserSignUpModel): AuthResult {
        return try {
            if(checkDuplicatePhoneNumber(user.phoneNumber)){
                AuthResult.Error(errorMessage = "Phone number is already in use", errorCode = ErrorCode.DUPLICATE_PHONE_NUMBER)
            }else{
            val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()
                val userData = UserData(
                    username = user.username,
                    userId = result.user?.uid ?: "test_id",
                    email = user.email,
                    phoneNumber = user.phoneNumber,
                    profilePictureUrl = null
                )
                addUserInformationToDatabase(userData)

                AuthResult.Success(
                    data = userData
                )
            }
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

    override suspend fun addUserInformationToDatabase(userData: UserData) {
        db.child("users").child(userData.userId ?: "error").setValue(userData)
    }

    override suspend fun signUserIn(user: UserLoginCredentials): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(user.email, user.password).await()
            AuthResult.Success(
                data = UserData(
                    username = result.user?.displayName ?: "Unknown user",
                    userId = result.user?.uid ?: "test_id",
                    email = user.email,
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

    override suspend fun checkDuplicatePhoneNumber(phoneNumber: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val usersReference = db.child("users")

            usersReference.orderByChild("phoneNumber").equalTo(phoneNumber)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val isDuplicate = snapshot.exists()
                        continuation.resume(isDuplicate)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("snapshot", "cancelled")
                        continuation.resume(false) // Handle the error accordingly
                    }
                })
        }
    }

    override suspend fun getUserById(id: String): UserData {
        val dbUser = db.child("users").child(id).get().await()
        val userData = with(dbUser) {
            UserData(
                userId = child("userId").value.toString(),
                username = child("username").value.toString(),
                email = child("email").value.toString(),
                profilePictureUrl = child("profilePictureUrl").value.toString(),
            )
        }
        return userData
    }

}