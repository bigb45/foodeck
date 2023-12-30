package com.example.welcome

import android.util.Log.d
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.UserDetailsModel
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

    private val _user: MutableStateFlow<UserDetailsModel> = MutableStateFlow(UserDetailsModel(userId = ""))
    val user: StateFlow<UserDetailsModel> = _user

    init {
        getUserFromId(userId)
    }

    fun getUserFromId(userId: String) {
        viewModelScope.launch {
            _user.value = getUser(userId)
            d("error", _user.value.toString())
        }
    }
}
