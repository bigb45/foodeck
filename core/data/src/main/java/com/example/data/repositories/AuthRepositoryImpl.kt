package com.example.data.repositories


import com.example.data.models.SignInAuthResponseModel
import com.example.data.models.SignupAuthResponseModel
import com.example.data.models.TokenAuthResponseModel
import com.example.data.models.UserDetailsModel
import com.example.data.models.UserSignInModel
import com.example.data.models.UserSignUpModel
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

    override fun createUser(user: UserSignUpModel): Flow<SignupAuthResponseModel> {

        return flow {
            if (checkDuplicatePhoneNumber(user.phoneNumber)) {
                throw DuplicatePhoneNumberError()
            }
            val result =
                auth.createUserWithEmailAndPassword(user.email, user.password).await()
            val userData = UserDetailsModel(
                username = user.username,
                userId = result.user?.uid ?: "test_id",
                email = user.email,
                phoneNumber = user.phoneNumber,
                profilePictureUrl = null
            )
            addUserInformation(userData)
            emit(SignupAuthResponseModel.Loading)
        }

    }


    class DuplicatePhoneNumberError : Exception() {
        override val message: String = "The phone number is already in use."
    }

    override suspend fun addUserInformation(userData: UserDetailsModel) {
        db.child("users").child(userData.userId ?: "error").setValue(userData)
    }

    override suspend fun authenticateUserWithToken(token: String, provider: String): Flow<TokenAuthResponseModel> {
        TODO("Not yet implemented")
    }

    override suspend fun signUserIn(user: UserSignInModel): Flow<SignInAuthResponseModel> {
        return flow {
            val result = auth.signInWithEmailAndPassword(user.email, user.password).await()
            val userData = UserDetailsModel(
                username = result.user?.displayName ?: "Unknown user",
                userId = result.user?.uid ?: "test_id",
                email = user.email,
                profilePictureUrl = null
            )

//            emit(userData)
            emit(SignInAuthResponseModel.Loading)
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
                        continuation.resume(false)
                    }
                })
        }
    }

    override suspend fun getUserById(id: String): UserDetailsModel {
        val dbUser = db.child("users").child(id).get().await()
        val userData = with(dbUser) {
            UserDetailsModel(
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