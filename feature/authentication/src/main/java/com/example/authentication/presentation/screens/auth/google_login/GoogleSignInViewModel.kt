package com.example.authentication.presentation.screens.auth.google_login

import androidx.lifecycle.ViewModel
import com.example.authentication.presentation.screens.auth.AuthResult
import com.example.authentication.presentation.screens.auth.UserData

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GoogleSignInViewModel : ViewModel() {
    private val _state = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val state: StateFlow<AuthResult> = _state

    fun onSignInResult(result: AuthResult) {
        _state.value = result
    }

    fun setSignedInUser(userData: UserData){
        _state.value = AuthResult.Success(data = userData)
    }


    fun resetState(){
        _state.value = AuthResult.Loading
    }
}