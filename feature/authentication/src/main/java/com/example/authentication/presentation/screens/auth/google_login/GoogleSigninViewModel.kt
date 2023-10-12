package com.example.authentication.presentation.screens.auth.google_login

import androidx.lifecycle.ViewModel
import com.example.authentication.presentation.screens.auth.SignInResult
import com.example.authentication.presentation.screens.auth.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GoogleSigninViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult){
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }


//    fun resetState(){
//        _state.update {
//                SignInState()
//        }
//    }
}