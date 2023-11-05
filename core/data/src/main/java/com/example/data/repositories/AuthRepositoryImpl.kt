package com.example.data.repositories


import com.example.data.data.UserData
import com.example.data.data.UserLoginCredentials
import com.example.data.data.UserSignUpModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume


class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    private val db = Firebase.database.reference

    override fun createUser(user: UserSignUpModel): Flow<UserData> {
        return flow {
            if (checkDuplicatePhoneNumber(user.phoneNumber)) {
                throw DuplicatePhoneNumberError()
            }
            val result =
                auth.createUserWithEmailAndPassword(user.email, user.password).await()
            val userData = UserData(
                username = user.username,
                userId = result.user?.uid ?: "test_id",
                email = user.email,
                phoneNumber = user.phoneNumber,
                profilePictureUrl = null
            )
            addUserInformationToDatabase(userData)
            emit(userData)
        }

    }


    class DuplicatePhoneNumberError : Exception() {
        override val message: String = "The phone number is already in use."
    }

    override suspend fun addUserInformationToDatabase(userData: UserData) {
        db.child("users").child(userData.userId ?: "error").setValue(userData)
    }

    override fun signUserIn(user: UserLoginCredentials): Flow<UserData> {
        return flow {
            val result = auth.signInWithEmailAndPassword(user.email, user.password).await()
            val userData = UserData(
                username = result.user?.displayName ?: "Unknown user",
                userId = result.user?.uid ?: "test_id",
                email = user.email,
                profilePictureUrl = null
            )

            emit(userData)
        }


    }

    override suspend fun signUserOut(): Flow<Boolean> {
        return flow{false}
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
                phoneNumber = child("phoneNumber").value.toString(),
                email = child("email").value.toString(),
                profilePictureUrl = child("profilePictureUrl").value.toString(),
            )
        }
        return userData
    }

}