package com.example.home.welcome

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.data.UserData
import com.example.data.models.AuthResult
import com.example.domain.use_cases.GetUserFromIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUser: GetUserFromIdUseCase,
) : ViewModel() {

    private val userId: String =
        URLDecoder.decode(savedStateHandle["userId"], Charsets.UTF_8.name())

    private val _user: MutableStateFlow<UserData> = MutableStateFlow(UserData(userId = ""))
    private val _authState: MutableStateFlow<AuthResult> = MutableStateFlow(AuthResult.Loading)
    val user: StateFlow<UserData> = _user
    val authState: StateFlow<AuthResult> = _authState

    init {
        getUserFromId(userId)
    }

    private fun getUserFromId(userId: String) {
        _authState.value = AuthResult.Loading
        viewModelScope.launch {
            _user.value = getUser(userId)
        }
    }
}
