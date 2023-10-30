package com.example.authentication.facebook_login

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.AuthResult
import com.example.data.data.UserData
import com.example.domain.use_cases.AddUserInformationUseCase
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FacebookLoginViewModel @Inject constructor(private val addAdditionalUserInformation: AddUserInformationUseCase) : ViewModel() {
    private val _authResult = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val authResult: StateFlow<AuthResult> = _authResult

    fun handleLogInSuccess(result: LoginResult) {
        val graphRequest = GraphRequest.newMeRequest(
            result.accessToken
        ) { user, _ ->
            if (user != null) {
                val userId = user.getString("id")
                val username = user.getString("name")
                val email = user.getString("email")
                val phoneNumber = user.getString("phoneNumber")
                val profilePictureUrl = "https://graph.facebook.com/$userId/picture?type=large"

                val data = UserData(
                    userId = userId,
                    email = email,
                    phoneNumber = phoneNumber,
                    username = username,
                    profilePictureUrl = profilePictureUrl
                )
                addUserInfo(data)
                _authResult.value = AuthResult.Success(
                    data = data
                )
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "email,phoneNumber,id,name")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()

    }

    private fun addUserInfo(data: UserData) {
        viewModelScope.launch {
//          TODO: handle error here ↘️↘️
            addAdditionalUserInformation(data)
        }
    }

    fun cancelLogin() {
        _authResult.value = AuthResult.Cancelled
    }

    fun loginError(exception: FacebookException) {
        _authResult.value = AuthResult.Error(errorMessage = exception.message ?: "Unknown error.")
    }

    fun setStateToLoading() {
        _authResult.value = AuthResult.Loading
    }
}