package com.example.authentication

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log.d
import com.example.data.models.UserDetails
import com.example.fooddelivery.authentication.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient (
    private val context: Context,
    private val oneTapClient: SignInClient,
    ) {

    private val auth = Firebase.auth
    suspend fun signInWithIntent(intent: Intent): String {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        d("error", googleIdToken.toString())
        googleIdToken?.let{
            return googleIdToken
        }

        return ""
//        return try {
//            val user = auth.signInWithCredential(googleCredentials).await().user
//            if (user != null) {
//                val data = user.run {
//                    UserDetailsModel(
//                        userId = uid,
//                        email = email,
//                        username = displayName ?: "default user",
//                        profilePictureUrl = photoUrl?.toString(),
//                    )
//                }
//                AuthResult.Success(data = data)
//            } else {
//                return AuthResult.Error("Unable to sign in")
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            if (e is CancellationException) throw e
//            AuthResult.Error(errorMessage = e.message ?: "Unknown Error")
//        }
    }


    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder().setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id)).build()
        ).setAutoSelectEnabled(true).build()
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserDetails? = auth.currentUser?.run {
        UserDetails(
            userId = uid,
            name = displayName ?: "default user",
            profilePictureUrl = photoUrl?.toString()
        )
    }

}