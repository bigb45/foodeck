package com.example.authentication

sealed class LoginMethodsScreenState {
    object SignedOut: LoginMethodsScreenState()
    object Loading: LoginMethodsScreenState()
    data class Error(val errorMessage: String) : LoginMethodsScreenState()

}