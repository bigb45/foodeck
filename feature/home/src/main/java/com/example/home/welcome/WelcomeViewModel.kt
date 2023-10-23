package com.example.home.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.AuthResult
import com.example.data.data.UserData
import com.example.domain.use_cases.GetUserFromIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val getUser: GetUserFromIdUseCase): ViewModel() {
    private val _user: MutableStateFlow<UserData> = MutableStateFlow(UserData(userId = ""))
    private val _authState: MutableStateFlow<AuthResult> = MutableStateFlow(AuthResult.Loading)
    val user: StateFlow<UserData> = _user
    val authState: StateFlow<AuthResult> = _authState

    fun getUserFromId(userId: String){
        _authState.value = AuthResult.Loading
        viewModelScope.launch {
            _user.value = getUser(userId)
        }
    }
}
