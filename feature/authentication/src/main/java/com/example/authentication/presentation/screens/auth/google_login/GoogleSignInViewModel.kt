package com.example.authentication.presentation.screens.auth.google_login

import androidx.lifecycle.ViewModel
import com.example.data.models.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val state: StateFlow<AuthResult> = _state

    fun onSignInResult(result: AuthResult) {
        _state.value = result
    }

    fun resetState(){
        _state.value = AuthResult.Loading
    }
}