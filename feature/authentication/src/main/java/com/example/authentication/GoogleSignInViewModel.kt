package com.example.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data.UserData
import com.example.data.models.AuthResult
import com.example.domain.use_cases.AddUserInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(private val addAdditionalUserInformation: AddUserInformationUseCase) : ViewModel() {
    private val _state = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val state: StateFlow<AuthResult> = _state

    fun onSignInResult(result: AuthResult) {
        when (result) {
            is AuthResult.Success -> {
                addUserInfo(result.data)
            }

            else -> {}
        }
        _state.value = result
    }

    private fun addUserInfo(data: UserData) {
        viewModelScope.launch {
//          TODO: handle error here ↘️↘️
            addAdditionalUserInformation(data)
        }
    }
}