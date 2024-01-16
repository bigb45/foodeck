package com.example.welcome

import android.util.Log.d
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.UserDetailsModel
import com.example.domain.use_cases.GetUserFromIdUseCase
import com.example.welcome.navigation.userIdArgument
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

    private val _uiState = MutableStateFlow<WelcomeScreenUiState>(WelcomeScreenUiState.Loading)
    private val userId: String =
        URLDecoder.decode(savedStateHandle[userIdArgument], Charsets.UTF_8.name())

    val uiState: StateFlow<WelcomeScreenUiState> = _uiState

    init {
        getUserFromId(userId)
    }

    private fun getUserFromId(userId: String) {
        viewModelScope.launch {
            val user = getUser(userId)
            _uiState.value = WelcomeScreenUiState.Success(user)

            d("error", user.toString())
        }
    }
}
